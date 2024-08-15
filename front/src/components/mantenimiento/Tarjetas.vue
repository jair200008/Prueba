<template>
  <div class="tarjeta-mantenimiento">
    <h3>Mantenimiento</h3>
    <p><strong>Estado:</strong> {{ getEstado(mantenimiento.estado) }}</p>
    <p><strong>Vehículo:</strong> {{ mantenimiento.vehiculo.placa }}</p>
    <p><strong>Fecha:</strong> {{ formatearFecha(mantenimiento.fecha) }}</p>
    <p><strong>Mecánico:</strong> {{ mantenimiento.mecanico.primer_nombre }} {{ mantenimiento.mecanico.primer_apellido }}</p>
    <p><strong>Presupuesto:</strong> {{ formatPresupuesto(mantenimiento.presupuesto) }}</p>
    <button @click="abrirModal" class="btn-detalles">Cotización</button>
    <button @click="mostrarCarrito = !mostrarCarrito" class="btn-carrito">Ver Carrito ({{ carrito.length }})</button>

    <div v-if="mostrarCarrito" class="carrito">
      <h3>Carrito de Repuestos y Servicios</h3>
      <ul>
        <li v-for="(elemento, index) in carrito" :key="index">
          {{ elemento.nombre }} - {{ formatPresupuesto(elemento.precio) }}
          <button @click="eliminarElemento(index)">Eliminar</button>
        </li>
      </ul>
      <p><strong>Total:</strong> {{ formatPresupuesto(totalCarrito) }}</p>
      <button @click="realizarCotizacion" class="btn-cotizar">Realizar Cotización</button>
    </div>

    <div v-if="mostrarModal" class="modal">
      <div class="modal-content">
        <span @click="cerrarModal" class="close">&times;</span>
        <h3>Añadir Repuesto o Servicio</h3>

        <div class="radio-buttons">
          <input type="radio" id="repuesto" value="repuesto" v-model="tipoSeleccionado">
          <label for="repuesto">Repuesto</label>
          <input type="radio" id="servicio" value="servicio" v-model="tipoSeleccionado">
          <label for="servicio">Servicio</label>
        </div>

        <form @submit.prevent="agregarElemento">
          <div v-if="tipoSeleccionado === 'repuesto'" class="form-group">
            <label for="nombreRepuesto">Nombre del Repuesto:</label>
            <input v-model="nuevoElemento.nombre_repuesto" id="nombreRepuesto" @input="buscarCoincidencias" required>

            <ul v-if="coincidencias.length" class="coincidencias-lista">
              <li v-for="coincidencia in coincidencias" :key="coincidencia.id" @click="seleccionarCoincidencia(coincidencia)">
                {{ coincidencia.nombre }}
              </li>
            </ul>

            <label for="precioUnitario" :disabled="campoBloqueado">Precio Unitario:</label>
            <input v-model="nuevoElemento.precio_unitario" id="precioUnitario" type="number" :disabled="campoBloqueado" required>

            <label for="unidadesInventario">Unidades en Inventario:</label>
            <input v-model="nuevoElemento.unidades_inventario" id="unidadesInventario" type="number" required>

            <label for="proveedor">Proveedor:</label>
            <input v-model="nuevoElemento.proveedor" id="proveedor" required>

            <button v-if="!campoBloqueado" type="button" @click="agregarACarrito" class="btn-carrito">Añadir al Carrito</button>
          </div>

          <div v-if="tipoSeleccionado === 'servicio'" class="form-group">
            <label for="nombreServicio">Nombre del Servicio:</label>
            <input v-model="nuevoElemento.nombre_servicio" id="nombreServicio" @input="buscarCoincidencias" required>

            <ul v-if="coincidencias.length" class="coincidencias-lista">
              <li v-for="coincidencia in coincidencias" :key="coincidencia.id" @click="seleccionarCoincidencia(coincidencia)">
                {{ coincidencia.nombre }}
              </li>
            </ul>

            <label for="precio" :disabled="campoBloqueado">Precio:</label>
            <input v-model="nuevoElemento.precio" id="precio" type="number" :disabled="campoBloqueado" required>


            <button v-if="!campoBloqueado" type="button" @click="agregarACarrito" class="btn-carrito">Añadir al Carrito</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import RepuestosService from '@/store/modules/servicios.js';

export default {
  name: 'Tarjetas',
  props: {
    mantenimiento: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      mostrarModal: false,
      mostrarCarrito: false,
      tipoSeleccionado: 'repuesto',
      nuevoElemento: {
        nombre_repuesto: '',
        precio_unitario: null,
        unidades_inventario: null,
        proveedor: '',
        nombre_servicio: '',
        precio: null,
        comentario: ''
      },
      coincidencias: [],
      campoBloqueado: false,
      carrito: []
    };
  },
  computed: {
    totalCarrito() {
      return this.carrito.reduce((total, elemento) => total + (elemento.precio || 0), 0);
    }
  },
  methods: {
    formatearFecha(fecha) {
      return new Date(fecha).toLocaleDateString();
    },
    abrirModal() {
      this.mostrarModal = true;
    },
    cerrarModal() {
      this.mostrarModal = false;
      this.resetForm();
    },
    async buscarCoincidencias() {
      const query = this.tipoSeleccionado === 'repuesto' ? this.nuevoElemento.nombre_repuesto : this.nuevoElemento.nombre_servicio;
      try {
        const data = await RepuestosService.buscarCoincidencias(query);
        this.coincidencias = data;
      } catch (error) {
        console.error('Error al buscar coincidencias:', error);
      }
    },
    seleccionarCoincidencia(coincidencia) {
      this.campoBloqueado = true;
      if (this.tipoSeleccionado === 'repuesto') {
        this.nuevoElemento.nombre_repuesto = coincidencia.nombre;
        this.nuevoElemento.precio_unitario = coincidencia.precio;
      } else {
        this.nuevoElemento.nombre_servicio = coincidencia.nombre;
        this.nuevoElemento.precio = coincidencia.precio;
      }
    },
    agregarACarrito() {
      const elemento = {
        nombre: this.tipoSeleccionado === 'repuesto' ? this.nuevoElemento.nombre_repuesto : this.nuevoElemento.nombre_servicio,
        precio: this.tipoSeleccionado === 'repuesto' ? this.nuevoElemento.precio_unitario : this.nuevoElemento.precio,
        tipo: this.tipoSeleccionado
      };
      this.carrito.push(elemento);
      this.resetForm();
    },
    agregarElemento() {
      if (this.tipoSeleccionado === 'repuesto') {
        this.mantenimiento.presupuesto += this.nuevoElemento.precio_unitario;
      } else if (this.tipoSeleccionado === 'servicio') {
        this.mantenimiento.presupuesto += this.nuevoElemento.precio;
      }
      this.cerrarModal();
    },
    eliminarElemento(index) {
      this.carrito.splice(index, 1);
    },
    resetForm() {
      this.campoBloqueado = false;
      this.nuevoElemento = {
        nombre_repuesto: '',
        precio_unitario: null,
        unidades_inventario: null,
        proveedor: '',
        nombre_servicio: '',
        precio: null,
        comentario: ''
      };
      this.coincidencias = [];
    },
    formatPresupuesto(presupuesto) {
      return presupuesto !== null ? `$${presupuesto.toFixed(2)}` : 'No disponible';
    },
    getEstado(estado) {
      return estado === 0 ? 'Pendiente' : 'Completado';
    },
    async realizarCotizacion() {
      try {
        for (const item of this.carrito) {
          if (item.tipo === 'repuesto') {
            await RepuestosService.enviarRepuesto(item);
          } else if (item.tipo === 'servicio') {
            await RepuestosService.enviarServicio(item);
          }
        }
        this.carrito = [];
        alert('Cotización realizada con éxito');
      } catch (error) {
        console.error('Error al realizar la cotización:', error);
        alert('Hubo un error al realizar la cotización');
      }
    },
  }
};
</script>

<style scoped>
.form-group {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}

.form-group label {
  margin-bottom: 5px;
  font-weight: bold;
  color: var(--color-primary);
}

.form-group input,
.form-group textarea {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
  width: 100%;
  box-sizing: border-box;
}

.form-group input:disabled,
.form-group textarea:disabled {
  background-color: #f0f0f0;
}

.form-group textarea {
  resize: vertical;
  min-height: 80px;
}

.btn-cotizar {
  margin-top: 20px;
  width: 100%;
}

.tarjeta-mantenimiento {
  margin: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: var(--vt-c-indigo);
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  width: 300px;
}

.tarjeta-mantenimiento h3 {
  margin-top: 0;
  color: var(--color-primary);
  font-weight: bold;
}

.tarjeta-mantenimiento p {
  color: var(--color-primary);
  margin: 8px 0;
}

.btn-detalles,
.btn-carrito,
.btn-cotizar {
  background-color: #4CAF50;
  border: none;
  color: white;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin-top: 10px;
  cursor: pointer;
  border-radius: 5px;
}

.btn-cotizar {
  background-color: #007bff;
}

.btn-cotizar:hover {
  background-color: #0056b3;
}

.modal {
  display: block;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  background-color: var(--vt-c-indigo);
  margin: 10% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 500px;
  border-radius: 8px;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: black;
}

.radio-buttons {
  margin-bottom: 10px;
}

.radio-buttons label {
  margin-right: 10px;
}

.coincidencias-lista {
  list-style-type: none;
  margin: 0;
  padding: 0;
  max-height: 100px;
  overflow-y: auto;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.coincidencias-lista li {
  padding: 8px;
  cursor: pointer;
}

.coincidencias-lista li:hover {
  background-color: #f0f0f0;
}

.carrito {
  margin-top: 20px;
  width: 100%;
  text-align: left;
}

.carrito h3 {
  margin-bottom: 10px;
}

.carrito ul {
  list-style-type: none;
  padding: 0;
}

.carrito li {
  padding: 10px;
  background-color: var(--color-hover);
  color: var(--vt-c-indigo);
  font-weight: bold;
  border: 1px solid #ddd;
  margin-bottom: 5px;
  border-radius: 4px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.carrito button {
  background-color: #ff4c4c;
  border: none;
  color: white;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 3px;
}

.carrito button:hover {
  background-color: #e63939;
}
</style>