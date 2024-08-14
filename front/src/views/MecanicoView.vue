<script setup>
import {ref, computed, onMounted} from 'vue';
import {useStore} from 'vuex';
import AlertComponent from '../components/navbar/AlertComponent.vue';
import MecanicoForm from "@/components/clientes/MecanicoForm.vue";
import {useAlerts} from "@/composables/useAlert.js";
import GenericTable from "@/components/navbar/GenericTable.vue";


const store = useStore();
const mecanicos = computed(() => store.getters.allMecanicos);
const {alertMessage, alertType, showAlert} = useAlerts();

const selectedMecanico = ref(null);
const isEditing = ref(false);

const fetchMecanicos = () => {
  store.dispatch('fetchMecanicos');
};

const headers = [
  {key: 'tipo_documento', label: 'Tipo de Documento'},
  {key: 'documento', label: 'Número de Documento'},
  {key: 'nombre_completo', label: 'Nombre'},
  {key: 'email', label: 'Email'},
  {key: 'celular', label: 'Teléfono'},
  {key: 'estado', label: 'Estado'},
  {key: 'direccion', label: 'Dirección'},
];

const dropdownOptions = [
  {action: 'edit', label: 'Editar', class: 'edit'},
  {action: 'delete', label: 'Eliminar', class: 'delete'}
];

const handleDropdownAction = (action, mecanico) => {
  switch (action) {
    case 'edit':
      editCliente(mecanico);
      break;
    case 'delete':
      confirmDeleteCliente(mecanico);
      break;
  }
};

const editCliente = (mecanico) => {
  selectedMecanico.value = {...mecanico};
  isEditing.value = true;
};

const handleSave = (message) => {
  if (!message.includes('Error')) {
    showAlert(message, 'exito');
  } else {
    showAlert(message, 'error');
  }
  fetchMecanicos();
  cancelEdit();
};

const confirmDeleteCliente = (mecanico) => {
  if (confirm(`¿Estás seguro de que deseas eliminar al cliente con documento: ${mecanico.documento}?`)) {
    deleteMecanico(mecanico.codigo);
  }
};

const deleteMecanico = async (id) => {
  try {
    const message = await store.dispatch('deleteMecanico', id);
    showAlert(message, 'exito');
    fetchMecanicos();
  } catch (error) {
    showAlert('Ocurrió un error al intentar eliminar el cliente.', 'error');
  }
};

const cancelEdit = () => {
  selectedMecanico.value = null;
  isEditing.value = false;
};

onMounted(fetchMecanicos);
</script>

<template>
  <div class="container">
    <AlertComponent :message="alertMessage" :type="alertType"/>
    <h2>Mecánicos</h2>
    <div class="sub-container">
      <MecanicoForm
          :mecanico="selectedMecanico"
          :isEditing="isEditing"
          @save="handleSave"
          @cancel="cancelEdit"
      />
      <GenericTable
          :headers="headers"
          :data="mecanicos"
          :actions="dropdownOptions"
          @action="handleDropdownAction"
      />
    </div>
  </div>
</template>

<style scoped>
.sub-container {
  display: flex;
  gap: 20px;
}
</style>