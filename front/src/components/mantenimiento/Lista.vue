<template>
  <div>
    <label for="dropdown">Seleccione un mecánico:</label>
    <select id="dropdown" v-model="selectedMecanico" @change="onChangeMecanico">
      <option value="">Seleccione un mecánico</option>
      <option v-for="mecanico in allMecanicos" :key="mecanico.codigo" :value="mecanico.codigo">
        NOM: {{ mecanico.nombre_completo }} | DOC:  {{ mecanico.documento }}
      </option>
    </select>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'DropdownList',
  data() {
    return {
      selectedMecanico: '',
    };
  },
  computed: {
    ...mapGetters(['allMecanicos']),
  },
  methods: {
    onChangeMecanico() {
      if (this.selectedMecanico) {
        this.$emit('change', this.selectedMecanico);
      }
    },
  },
  mounted() {
    this.$store.dispatch('fetchMecanicos')
        .catch(error => {
          console.error('Error al cargar mecánicos:', error);
        });
  },
};
</script>

<style scoped>
select {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
}
</style>