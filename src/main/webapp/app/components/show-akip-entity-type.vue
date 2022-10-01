<template>
  <div>
    <span
      :class="{
        'badge rounded-pill bg-primary': value == 'DOMAIN',
        'badge rounded-pill bg-warning': value == 'PROCESS_BINDING',
        'badge rounded-pill bg-info': value == 'START_FORM',
        'badge rounded-pill bg-danger': value == 'USER_TASK',
        'badge rounded-pill bg-dark': value == 'SERVICE_TASK',
      }"
    >
      <font-awesome-icon v-if="value == 'SERVICE_TASK'" icon="cogs"></font-awesome-icon>
      <font-awesome-icon v-if="value == 'START_FORM'" icon="forward"></font-awesome-icon>
      <font-awesome-icon v-if="value == 'USER_TASK'" icon="user"></font-awesome-icon>
      <font-awesome-icon v-if="value == 'PROCESS_BINDING'" icon="share"></font-awesome-icon>
      <font-awesome-icon v-if="value == 'DOMAIN'" icon="file"></font-awesome-icon>
      &nbsp;<span class="badge rounded-pill" v-text="$t('akipGenerationWebApp.TypeEntity.' + value)" />
    </span>
  </div>
</template>

<script lang="ts" src="./show-akip-entity-type.component.ts"></script>

<style scoped>
.badge {
  color: white;
  padding: 0.35em 0.65em;
  font-weight: bold;
  font-size: 90%;
}
</style>
