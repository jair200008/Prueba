<template>
  <div class="mantenimiento-container">
    <h2>Mantenimiento</h2>
    <DropdownList @change="handleMecanicoChange" />
    <div v-if="mantenimientoPorMecanico">
      <Tarjetas
          :mantenimiento="mantenimientoPorMecanico"
          @ver-detalles="verDetalles"
      />
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import DropdownList from '@/components/mantenimiento/Lista.vue';
import Tarjetas from '@/components/mantenimiento/Tarjetas.vue';

export default {
  name: 'Mantenimiento',
  components: {
    DropdownList,
    Tarjetas,
  },
  computed: {
    ...mapGetters(['mantenimientoPorMecanico']),
  },
  methods: {
    ...mapActions(['fetchMaintenancesByMecanico']),
    handleMecanicoChange(codigoMecanico) {
      if (codigoMecanico) {
        this.fetchMaintenancesByMecanico(codigoMecanico);
      }
    },
    verDetalles(codigo) {
      console.log('Ver detalles del mantenimiento:', codigo);
    },
  },
};
</script>

<style scoped>
.mantenimiento-container {
  padding: 20px;
}
</style>