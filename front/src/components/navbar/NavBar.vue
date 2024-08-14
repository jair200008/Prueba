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
        <router-link to="/vehiculos">Vehículos</router-link>
      </li>
      <li>
        <router-link to="/">Mantenimiento</router-link>
      </li>

      <li>
        <router-link to="/">Marcas</router-link>
      </li>
      <li>
        <router-link to="/">Repuestos</router-link>
      </li>
      <li>
        <router-link to="/">Servicios</router-link>
      </li>
      <li>
        <router-link to="/">Foto</router-link>
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
  border-radius: 5px;
  padding: 8px;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

li:hover {
  cursor: pointer;
  transform: scale(1.05);

}
a{
  color: var(--color-primary);
  font-weight: bold;
  transition: color 0.3s ease;
}
li:hover a {
  color: var(--vt-c-indigo);
}

@media (min-width: 1024px) {
}
</style>