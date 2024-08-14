<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  options: {
    type: Array,
    required: true
  }
});

const emit = defineEmits(['action']);

const handleAction = (action) => {
  emit('action', action); // Asegúrate de que el evento se emita correctamente
};
</script>

<template>
  <div class="dropdown">
    <button class="drop-button">Opciones</button>
    <div class="dropdown-content">
      <a
          v-for="option in options"
          :key="option.action"
          href="#"
          @click.prevent="handleAction(option.action)"
      :class="option.class"
      >
      {{ option.label }}
      </a>
    </div>
  </div>
</template>


<style scoped>

.drop-button {
  background-color: #4CAF50;
  padding: 10px 20px;
  border-radius: 8px;
  color: white;
  border: none;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease, transform 0.3s ease;
}


.drop-button:hover {
  background-color: #45a049;
  transform: scale(1.05);
}


.dropdown-content {
  display: none;
  position: absolute;
  background-color: white;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  min-width: 120px;
  z-index: 1000;
  transition: opacity 0.3s ease, transform 0.3s ease;
  opacity: 0;
  transform: translateY(-10px);
}


.dropdown:hover .dropdown-content {
  display: block;
  opacity: 1;
  transform: translateY(-40%) translateX(-100%);

}


.dropdown-content a {
  color: #333;
  padding: 12px 16px;
  text-decoration: none;
  display: flex;
  align-items: center;
  font-size: 14px;
  transition: background-color 0.3s ease;
}

.dropdown-content a i {
  margin-right: 8px;
  color: #4CAF50;
}

.dropdown-content a:hover {
  background-color: var(--vt-c-indigo);
  color: white;
  border-radius: 8px;
}

.dropdown-content a.edit::before {
  content: '\f044';
  font-family: "FontAwesome",serif;
  margin-right: 8px;
}

.dropdown-content a.delete::before {
  content: '\f2ed';
  font-family: "FontAwesome",serif;
  margin-right: 8px;
}
</style>