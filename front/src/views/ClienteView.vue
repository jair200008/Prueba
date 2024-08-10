<script setup>
import { ref, computed, onMounted } from 'vue';
import { useStore } from 'vuex';
import ClienteForm from '../components/clientes/ClienteForm.vue';
import { useAlerts } from '../composables/useAlert.js';

const store = useStore();
const clientes = computed(() => store.getters.allClientes);
const { alertMessage, alertType, showAlert } = useAlerts();

const selectedCliente = ref(null);
const isEditing = ref(false);

const fetchClientes = () => {
  store.dispatch('fetchClientes');
};

const editCliente = (cliente) => {
  selectedCliente.value = { ...cliente };
  isEditing.value = true;
};

const handleSave = (message) => {
  if (message.includes('exitosamente')) {
    showAlert(message, 'exito');
  } else {
    showAlert(message, 'error');
  }
  fetchClientes();
  cancelEdit();
};

const confirmDeleteCliente = (cliente) => {
  if (confirm(`¿Estás seguro de que deseas eliminar al cliente con documento: ${cliente.documento}?`)) {
    deleteCliente(cliente.codigo);
  }
};

const deleteCliente = async (id) => {
  try {
    const message = await store.dispatch('deleteCliente', id);
    showAlert(message, 'exito');
    fetchClientes();
  } catch (error) {
    console.error('Error al eliminar el cliente:', error);
    showAlert('Ocurrió un error al intentar eliminar el cliente.', 'error');
  }
};

const cancelEdit = () => {
  selectedCliente.value = null;
  isEditing.value = false;
};

// Control para el dropdown
const isDropdownOpen = ref(false);

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

onMounted(fetchClientes);
</script>
<template>
  <div class="container">
    <div v-if="alertMessage" :class="['alerta', alertType]">
      <i :class="alertType === 'exito' ? 'fas fa-check-circle' : 'fas fa-exclamation-circle'" class="alert-icon"></i>
      <span>{{ alertMessage }}</span>
    </div>

    <h2>Clientes</h2>
    <div class="clientes">
      <ClienteForm
          :cliente="selectedCliente"
          :isEditing="isEditing"
          @save="handleSave"
          @cancel="cancelEdit"
      />

      <div class="clientes-list">
        <table>
          <thead>
          <tr>
            <th>Tipo de Documento</th>
            <th>Número de Documento</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Email</th>
            <th>Teléfono</th>
            <th>Dirección</th>
            <th>Acciones</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="cliente in clientes" :key="cliente.id">
            <td>{{ cliente.tipo_documento }}</td>
            <td>{{ cliente.documento }}</td>
            <td>{{ cliente.primer_nombre }} {{ cliente.segundo_nombre }}</td>
            <td>{{ cliente.primer_apellido }} {{ cliente.segundo_apellido }}</td>
            <td>{{ cliente.email }}</td>
            <td>{{ cliente.celular }}</td>
            <td>{{ cliente.direccion }}</td>
            <td>
              <div class="dropdown">
                <button class="dropbtn" @click="toggleDropdown">
                  <i class="fa-solid fa-list"></i> Opciones
                </button>
                <div v-if="isDropdownOpen" class="dropdown-content">
                  <a href="#" @click="editCliente(cliente)">
                    <i class="fa-solid fa-pencil-alt"></i> Editar
                  </a>
                  <a href="#" @click="confirmDeleteCliente(cliente)">
                    <i class="fas fa-trash-alt"></i> Eliminar
                  </a>
                </div>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

.clientes {
  display: flex;
  gap: 20px;
}

.cliente-form {
  flex: 0 0 25%; /* Reducido para dar más espacio a la tabla */
}

.clientes-list {
  flex: 0 0 70%;
  overflow: visible; /* Asegura que el contenido se muestre por encima */
}

table {
  width: 100%; /* Ajuste para que la tabla ocupe todo el espacio disponible */
  border-collapse: collapse;
  background-color: var(--color-background-mute);
  border-radius: 8px;
  overflow: hidden;
  margin-right: 20px;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: var(--color-background-soft);
  color: white;
  font-size: 14px;
}

tr:hover {
  background-color: var(--color-background-mute);
}

.dropdown {
  position: relative;
}

.dropbtn {
  background-color: var(--color-hover);
  color: white;
  padding: 6px 10px;
  font-size: 14px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: var(--vt-c-white);
  min-width: 160px;
  box-shadow: 0px 8px 16px rgba(0,0,0,0.2);
  z-index: 9999; /* Aumentar z-index */
  border-radius: 4px;
}

.dropdown-content a {
  color: #333;
  padding: 12px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {
  background-color: var(--color-background-mute);
}

.dropdown:hover .dropdown-content {
  display: block;
}

.alerta {
  display: flex;
  align-items: center;
  padding: 10px 20px;
  border-radius: 4px;
  margin-bottom: 20px;
  font-size: 16px;
}

.alerta.exito {
  background-color: #4caf50;
  color: white;
}

.alerta.error {
  background-color: #f44336;
  color: white;
}

.alert-icon {
  margin-right: 10px;
  font-size: 20px;
}
</style>
