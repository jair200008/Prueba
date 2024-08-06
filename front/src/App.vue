<script setup>

import NavBar from './components/navbar/NavBar.vue'
import {RouterLink, RouterView} from 'vue-router'
</script>

<template>
  <div class="app-container">
    <header>
      <NavBar/>
    </header>

    <main>
      <RouterView/>
    </main>
  </div>
</template>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;

}

header {
  border-radius: 10px;
  position: fixed;
  top: 10px;
  left: 10px;
  right: 10px;
  z-index: 10000;
  padding: 5px;
  background-color: var(--vt-c-black-mute);
  backdrop-filter: blur(3px);
}

main {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 80px;
}

@media (min-width: 1024px) {


}
</style>
