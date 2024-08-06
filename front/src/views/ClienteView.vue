<script setup>
import ClienteForm from '../components/clientes/ClienteForm.vue'
</script>

<template>
  <div class="container">
    <h2>Clientes</h2>
    <div class="clientes">
      <div class="cliente-form">
        <ClienteForm/>
      </div>
      <div class="clientes-list">
        <table>
          <thead>
          <tr>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Email</th>
            <th>Teléfono</th>
            <th>Dirección</th>
            <th>Fecha de nacimiento</th>
            <th>Acciones</th>
          </tr>
          </thead>
        </table>

      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
}

.clientes {
  display: flex;
  flex-direction: row;
  gap: 16px;
}
.clientes-list{
  flex: 0 0 40%;
}
.cliente-form{
  flex: 0 0 60%;
}
</style>