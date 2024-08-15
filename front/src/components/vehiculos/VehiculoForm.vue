<script setup>
import { ref, watch } from 'vue';
import { useStore } from 'vuex';
import { useAlerts } from '@/composables/useAlert.js';

const props = defineProps({
  vehiculo: {
    type: Object,
    default: () => ({
      placa: '',
      color: '',
      marca: {
        codigo: '',
        nombre_marca: '',
      },
      cliente: {
        tipo_documentos: '',
        documento: '',
        primer_nombre: '',
        segundo_nombre: '',
        primer_apellido: '',
        segundo_apellido: '',
        celular: '',
        direccion: '',
        email: ''
      },
      fotos: [
        {
          ruta: '',
          cod_mantenimiento: '',
          codigo: ''
        }
      ],
      presupuesto: '' // Añadir presupuesto
    })
  },
  isEditing: Boolean
});

const emit = defineEmits(['save', 'cancel']);
const store = useStore();
const {showAlert} = useAlerts();

// Usa reactive en lugar de ref para objetos complejos
const vehiculo = ref({...props.vehiculo});

watch(
    () => props.vehiculo,
    (newVehiculo) => {
      vehiculo.value = {
        ...newVehiculo,
        marca: {
          nombre_marca: newVehiculo?.marca?.nombre_marca || '',
        },
        cliente: {
          documento: newVehiculo?.cliente?.documento || '',
        },
        fotos: [
          {
            ...newVehiculo?.fotos?.[0] || {
              ruta: '',
              cod_mantenimiento: '',
              codigo: ''
            }
        ]
    ],
      presupuesto: newVehiculo?.presupuesto || '' // Asignar presupuesto
    }
      ;
    },
      {
        immediate: true, deep
      :
        true
      }
    )
      ;

      const handleSave = async () => {
        try {
          const action = props.isEditing ? 'updateVehiculo' : 'createVehiculo';
          const message = await store.dispatch(action, vehiculo.value);

          emit('save', message);
          if (!message.includes('Error')) {
            resetForm();
          }
        } catch (error) {
          showAlert('Ocurrió un error al procesar el vehículo.', 'error');
        }
      };

      const resetForm = () => {
        vehiculo.value = {
          placa: '',
          color: '',
          marca: {
            codigo: '',
            nombre_marca: '',
          },
          cliente: {
            tipo_documentos: '',
            documento: '',
            primer_nombre: '',
            segundo_nombre: '',
            primer_apellido: '',
            segundo_apellido: '',
            celular: '',
            direccion: '',
            email: ''
          },
          fotos: [
            {
              ruta: '',
              cod_mantenimiento: '',
              codigo: ''
            }
          ],
          presupuesto: '' // Resetear presupuesto
        };
      };

      const handleCancel = () => {
        resetForm();
        emit('cancel');
      };
</script>

<template>
  <form @submit.prevent="handleSave">
    <input v-model="vehiculo.placa" type="text" placeholder="Placa" required maxlength="10"/>
    <input v-model="vehiculo.color" type="text" placeholder="Color" required maxlength="20"/>
    <input v-model="vehiculo.marca.nombre_marca" type="text" placeholder="Nombre de la Marca" required maxlength="50"/>
    <input v-model="vehiculo.cliente.documento" type="number" placeholder="Documento del Cliente" required
           :readonly="isEditing"/>
    <input v-model="vehiculo.fotos[0].ruta" type="text" placeholder="Ruta de Fotos" maxlength="255" required/>
    <input v-model="vehiculo.presupuesto" type="number" placeholder="Presupuesto" required min="0" step="0.01"/>
    <div class="form-actions">
      <button type="submit" :class="['btn', isEditing ? 'guardar' : 'agregar']">
        {{ isEditing ? 'Guardar' : 'Agregar' }}
      </button>
      <button v-if="isEditing" @click="handleCancel" type="button" class="btn cancelar">Cancelar</button>
    </div>
  </form>
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
