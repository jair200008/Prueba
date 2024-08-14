<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  message: String,
  type: String
});
</script>

<template>
  <div v-if="message" :class="['alerta', type]">
    <i :class="type === 'exito' ? 'fas fa-check-circle' : 'fas fa-exclamation-circle'" class="alert-icon"></i>
    <span>{{ message }}</span>
  </div>
</template>

<style scoped>
.alerta {
  position: fixed;
  top: 20px;
  right: 20px;
  display: flex;
  align-items: center;
  padding: 10px 20px;
  border-radius: 4px;
  font-size: 16px;
  z-index: 2000000;
}

.alerta.exito {
  background-color: #4caf50;
  color: white;
}

.alerta.error {
  background-color: #f44336;
  color: white;
}

.alert-icon {
  margin-right: 10px;
  font-size: 20px;
}
</style>
