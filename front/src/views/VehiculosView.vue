<script setup>
import {ref, computed, onMounted} from 'vue';
import {useStore} from 'vuex';
import VehiculoForm from '../components/vehiculos/VehiculoForm.vue';
import AlertComponent from '../components/navbar/AlertComponent.vue';
import GenericTable from '../components/navbar/GenericTable.vue';
import {useAlerts} from '../composables/useAlert.js';

const store = useStore();
const vehiculos = computed(() => store.getters.allVehiculos);
const {alertMessage, alertType, showAlert} = useAlerts();

const selectedVehiculo = ref(null);
const isEditing = ref(false);

const fetchVehiculos = () => {

  store.dispatch('fetchVehiculos');
};

const headers = [
  {key: 'placa', label: 'Placa'},
  {key: 'color', label: 'Color'},
  {key: 'marca.nombre_marca', label: 'Marca'},
  {key: 'cliente.primer_nombre', label: 'Cliente'}
];
const dropdownOptions = [
  {action: 'edit', label: 'Editar', class: 'edit'},
  {action: 'delete', label: 'Eliminar', class: 'delete'}
];

const handleSave = (message) => {
  if (!message.includes('Error')) {
    showAlert(message, 'exito');
  } else {
    showAlert(message, 'error');
  }
  fetchVehiculos();
  isEditing.value = false;
};

const handleCancel = () => {
  selectedVehiculo.value = null;
  isEditing.value = false;
};
const editVehiculo = (vehiculo) => {
  selectedVehiculo.value = { ...vehiculo };
  isEditing.value = true;
};

const handleAction = (action, vehiculo) => {
  switch (action) {
    case 'edit':
      editVehiculo(vehiculo);
      break;
    case 'delete':
      confirmDeleteVehiculo(vehiculo);
      break;
  }
};

const confirmDeleteVehiculo = (vehiculo) => {
  if (confirm(`¿Estás seguro de que deseas eliminar al cliente con documento: ${vehiculo.placa}?`)) {
    deleteCliente(vehiculo.placa);
  }
};

const deleteCliente = async (id) => {
  try {
    const message = await store.dispatch('deleteVehiculo', id);
    showAlert(message, 'exito');
    fetchVehiculos();
  } catch (error) {
    showAlert('Ocurrió un error al intentar eliminar el vehiculo.', 'error');
  }
};

const cancelEdit = () => {
  selectedVehiculo.value = null;
  isEditing.value = false;
};

onMounted(fetchVehiculos);
</script>

<template>
  <div class="container">
    <h2>Gestión de Vehículos</h2>

    <AlertComponent :message="alertMessage" :type="alertType"/>

    <div class="sub-container">
      <VehiculoForm
          :vehiculo="selectedVehiculo"
          :isEditing="isEditing"
          @save="handleSave"
          @cancel="cancelEdit"
      />
      <GenericTable
          v-if="vehiculos && vehiculos.length"
          :headers="headers"
          :data="vehiculos"
          :actions="dropdownOptions"
          @action="handleAction"
      />
    </div>
  </div>
</template>

<style scoped>


</style>
