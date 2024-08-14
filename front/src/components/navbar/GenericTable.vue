<script setup>
import {defineProps, defineEmits} from 'vue';
import Dropdown from '@/components/navbar/Dropdown.vue';

const props = defineProps({
  headers: {
    type: Array,
    required: true
  },
  data: {
    type: Array,
    required: true
  },
  actions: {
    type: Array,
    default: () => []
  }
});

const emit = defineEmits(['action']);

const handleAction = (action, item) => {
  emit('action', action, item);
};

// Función para acceder a propiedades anidadas
const getNestedProperty = (obj, key) => {
  return key.split('.').reduce((o, i) => (o ? o[i] : null), obj);
};
</script>


<template>
  <div class="generic-table">
    <table>
      <thead>
      <tr>
        <th v-for="header in headers" :key="header.key">{{ header.label }}</th>
        <th v-if="actions.length">Acciones</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="item in data" :key="item.id">
        <td v-for="header in headers" :key="header.key">
          {{ getNestedProperty(item, header.key) }}
        </td>
        <td v-if="actions.length">
          <Dropdown
              :options="actions"
              @action="(action) => handleAction(action, item)"
          />
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>



<style scoped>
.generic-table {
  width: 100%;
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  background-color: var(--color-background-mute);
  border-radius: 8px;
}

th, td {
  padding: 12px;
  text-align: center;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: var(--color-background-soft);
  color: var(--color-primary);

  font-weight: bold;
}

tr:hover {
  background-color: var(--color-background-mute);
}
</style>