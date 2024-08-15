<script setup>
import { ref, watch } from 'vue';
import { useStore } from 'vuex';
import { useAlerts } from '@/composables/useAlert.js';

const props = defineProps({
  mecanico: {
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

const mecanico = ref({ ...props.mecanico });

watch(() => props.mecanico, (newMecanico) => {
  mecanico.value = { ...newMecanico };
}, { immediate: true });

const handleSave = async () => {
  try {
    const action = props.isEditing ? 'updateMecanico' : 'createMecanico';
    const message = await store.dispatch(action, mecanico.value);

    emit('save', message);
    if (!props.isEditing && message === 'MECANICO registrado exitosamente') {
      resetForm();
    }
  } catch (error) {
    showAlert(`Ocurrió un error al procesar el mecanico: ${error}`, 'error');
  }
};

const resetForm = () => {
  mecanico.value = {
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
  <form @submit.prevent="handleSave">
    <select v-model="mecanico.tipo_documento" required>
      <option value="" disabled>Seleccione Tipo de Documento</option>
      <option value="CC">Cédula de Ciudadanía</option>
      <option value="TI">Tarjeta de Identidad</option>
      <option value="EX">Extranjero</option>
      <option value="PA">Pasaporte</option>
    </select>
    <input v-model="mecanico.documento" type="number" placeholder="Número de Documento" required :readonly="isEditing" maxlength="20" />
    <input v-model="mecanico.primer_nombre" type="text" placeholder="Primer Nombre" required maxlength="30" />
    <input v-model="mecanico.segundo_nombre" type="text" placeholder="Segundo Nombre" maxlength="30" />
    <input v-model="mecanico.primer_apellido" type="text" placeholder="Primer Apellido" required maxlength="30" />
    <input v-model="mecanico.segundo_apellido" type="text" placeholder="Segundo Apellido" maxlength="30" />
    <input v-model="mecanico.celular" type="number" placeholder="Celular" required maxlength="10" />
    <input v-model="mecanico.direccion" type="text" placeholder="Dirección" maxlength="100" />
    <input v-model="mecanico.email" type="email" placeholder="Email" required maxlength="50" />
    <div class="form-actions">
      <button type="submit" :class="['btn', isEditing ? 'guardar' : 'agregar']">
        {{ isEditing ? 'Guardar' : 'Agregar' }}
      </button>
      <button v-if="isEditing" @click="handleCancel" type="button" class="btn cancelar">Cancelar</button>
    </div>
  </form>
</template>


<style scoped>
/* The same styles as before */
input, select {
  border: none;
  transition: background-color 0.2s ease, transform 0.2s ease;
}

input:focus, select:focus {
  transform: scale(1.05);
}

input:hover:not(:focus), select:hover:not(:focus) {
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

.btn:hover {
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
