<script setup>

</script>

<template>

  <nav>
    <div class="logo">
      <img src="../../assets/logo.svg" alt="logo">
    </div>
    <ul class="menu">
      <li>
        <router-link to="/inicio">Inicio</router-link>
      </li>
      <li>
        <router-link to="/clientes">Clientes</router-link>
      </li>
      <li>
        <router-link to="/mecanicos">Mecánicos</router-link>
      </li>
      <li>
        <router-link to="/mantenimiento">Mantenimiento</router-link>
      </li>
      <li>
        <router-link to="/vehicles">Vehículos</router-link>
      </li>
      <li>
        <router-link to="/marcas">Marcas</router-link>
      </li>
      <li>
        <router-link to="/repuestos">Repuestos</router-link>
      </li>
      <li>
        <router-link to="/servicios">Servicios</router-link>
      </li>
      <li>
        <router-link to="/foto">Foto</router-link>
      </li>
    </ul>

  </nav>
</template>

<style scoped>
nav {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
}

.logo {
  display: flex;
  align-items: center;
}

.logo img {
  height: auto;
  width: 2rem;
}

ul.menu {
  display: flex;
  justify-content: flex-end;
  list-style: none;
  gap: 1rem;
  margin: 0;
  padding: 0;
}

li {
  padding: 8px;
}

li:hover {
  border-radius: 5px;
  cursor: pointer;
}

@media (min-width: 1024px) {
}
</style>