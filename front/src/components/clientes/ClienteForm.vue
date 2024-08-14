<script setup>
import {ref, watch} from 'vue';
import {useStore} from 'vuex';
import {useAlerts} from '@/composables/useAlert.js';

const props = defineProps({
  cliente: {
    type: Object,
    default: () => ({
      tipo_documento: '',
      documento: '',
      primer_nombre: '',
      segundo_nombre: '',
      primer_apellido: '',
      segundo_apellido: '',
      celular: '',
      direccion: '',
      email: ''
    })
  },
  isEditing: Boolean
});

const emit = defineEmits(['save', 'cancel']);
const store = useStore();
const {showAlert} = useAlerts();

const cliente = ref({...props.cliente});

watch(() => props.cliente, (newCliente) => {
  cliente.value = {...newCliente};
}, {immediate: true});

const handleSave = async () => {
  try {
    const action = props.isEditing ? 'updateCliente' : 'createCliente';
    const message = await store.dispatch(action, cliente.value);

    emit('save', message);
    if (!props.isEditing && message === 'Cliente registrado exitosamente') {
      resetForm();
    }else{

    }
  } catch (error) {
    showAlert('Ocurrió un error al procesar el cliente.', 'error');
  }
};

const resetForm = () => {
  cliente.value = {
    tipo_documento: '',
    documento: '',
    primer_nombre: '',
    segundo_nombre: '',
    primer_apellido: '',
    segundo_apellido: '',
    celular: '',
    direccion: '',
    email: ''
  };
};

const handleCancel = () => {
  emit('cancel');
};
</script>


<template>
  <div class="cliente-form">
    <form @submit.prevent="handleSave">
      <input v-model="cliente.tipo_documento" type="text" placeholder="Tipo de Documento" required>
      <input
          v-model="cliente.documento"
          type="text"
          placeholder="Número de Documento"
          id="documento"
          required
          :readonly="isEditing"
      >
      <input v-model="cliente.primer_nombre" type="text" placeholder="Primer Nombre" required>
      <input v-model="cliente.segundo_nombre" type="text" placeholder="Segundo Nombre">
      <input v-model="cliente.primer_apellido" type="text" placeholder="Primer Apellido" required>
      <input v-model="cliente.segundo_apellido" type="text" placeholder="Segundo Apellido">
      <input v-model="cliente.celular" type="text" placeholder="Celular">
      <input v-model="cliente.direccion" type="text" placeholder="Dirección">
      <input v-model="cliente.email" type="email" placeholder="Email">

      <div class="form-actions">
        <button type="submit" :class="['btn', isEditing ? 'guardar' : 'agregar']">
          {{ isEditing ? 'Guardar' : 'Agregar' }}
        </button>
        <button v-if="isEditing" @click="handleCancel" type="button" class="btn cancelar">Cancelar</button>
      </div>
    </form>
  </div>
</template>

<style scoped>

input {
  border: none;
  transition: background-color 0.2s ease, transform 0.2s ease;
}
input:focus {
  transform: scale(1.05);
}
input:hover:not(:focus) {
  transform: scale(1.01);
}
.btn {
  padding: 12px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  color: white;
  font-size: 15px;
  font-weight: bold;
  margin: 2px;
  transition: background-color 0.2s ease, transform 0.2s ease;
}

.form-actions {
  display: flex;
  justify-content: space-between;
}

.btn.agregar {
  background-color: var(--btn-agregar);
  width: 100%;
  transition: background-color 0.3s ease, transform 0.3s ease;
}
.btn:hover{
  transform: scale(1.05);
}

.btn.agregar:hover {
  background-color: var(--color-hover);
}

.btn.guardar {
  background-color: var(--btn-guardar);
  width: 45%;
}

.btn.guardar:hover {
  background-color: var(--color-hover);
}

.btn.cancelar {
  background-color: var(--btn-cancelar);
  width: 45%;
}

.btn.cancelar:hover {
  background-color: var(--btn-hover-cancelar);
}
</style>