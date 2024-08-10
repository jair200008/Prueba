<script setup>
import { ref, watch } from 'vue';
import { useStore } from 'vuex';
import { useAlerts } from '../../composables/useAlert.js';

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
const { showAlert } = useAlerts();

const cliente = ref({ ...props.cliente });

watch(() => props.cliente, (newCliente) => {
  cliente.value = { ...newCliente };
}, { immediate: true });

const handleSave = async () => {
  try {
    const action = props.isEditing ? 'updateCliente' : 'createCliente';
    const message = await store.dispatch(action, cliente.value);

    emit('save', message);
    if (!props.isEditing && message === 'Cliente registrado exitosamente') {
      resetForm();
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
form{
  width: 100%;
}
.cliente-form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.form-actions {
  margin-top: 10px;
  display: flex;
  justify-content: center;
  gap: 8px;
}

.btn {
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  color: white;
}

.btn.agregar, .btn.guardar {
  background-color: #4CAF50;
  width: 70%;
}

.btn.guardar {
  background-color: #008CBA;
}

.btn.cancelar {
  background-color: #f44336;
}
</style>