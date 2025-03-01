<script setup>
import {ref, computed, onMounted} from 'vue';
import {useStore} from 'vuex';
import ClienteForm from '../components/clientes/ClienteForm.vue';
import AlertComponent from '../components/navbar/AlertComponent.vue';
import GenericTable from '../components/navbar/GenericTable.vue';
import {useAlerts} from '../composables/useAlert.js';

const store = useStore();
const clientes = computed(() => store.getters.allClientes);
const {alertMessage, alertType, showAlert} = useAlerts();

const selectedCliente = ref(null);
const isEditing = ref(false);

const fetchClientes = () => {
  store.dispatch('fetchClientes');
};

const headers = [
  {key: 'tipo_documento', label: 'Tipo de Documento'},
  {key: 'documento', label: 'Número de Documento'},
  {key: 'nombre_completo', label: 'Nombre'},
  {key: 'email', label: 'Email'},
  {key: 'celular', label: 'Teléfono'},
  {key: 'direccion', label: 'Dirección'},
];

const dropdownOptions = [
  {action: 'edit', label: 'Editar', class: 'edit'},
  {action: 'delete', label: 'Eliminar', class: 'delete'}
];

const handleDropdownAction = (action, cliente) => {
  switch (action) {
    case 'edit':
      editCliente(cliente);
      break;
    case 'delete':
      confirmDeleteCliente(cliente);
      break;
  }
};

const editCliente = (cliente) => {
  selectedCliente.value = {...cliente};
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

    showAlert('Ocurrió un error al intentar eliminar el cliente.', 'error');
  }
};

const cancelEdit = () => {
  selectedCliente.value = null;
  isEditing.value = false;
};

onMounted(fetchClientes);
</script>

<template>
  <div class="container">
    <AlertComponent :message="alertMessage" :type="alertType"/>
    <h2>Clientes</h2>
    <div class="clientes">
      <ClienteForm
          :cliente="selectedCliente"
          :isEditing="isEditing"
          @save="handleSave"
          @cancel="cancelEdit"
      />
      <div class="clientes-list">
        <GenericTable
            :headers="headers"
            :data="clientes"
            :actions="dropdownOptions"
            @action="handleDropdownAction"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.clientes {
  display: flex;
  gap: 20px;
}
</style>
