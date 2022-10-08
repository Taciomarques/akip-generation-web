<template>
  <div>
    <span
      :class="{
        'badge rounded-pill bg-primary': value == 'FINISHED',
        'badge rounded-pill bg-warning':
          value == 'WAITING_CONFIGURE_DOMAIN_ENTITY' ||
          value == 'WAITING_PROVIDE_PROCESS_BPMN' ||
          value == 'WAITING_CONFIGURE_PROCESS_BINDING' ||
          value == 'WAITING_CONFIGURE_START_FORM' ||
          value == 'WAITING_CONFIGURE_USERS_TASK' ||
          value == 'WAITING_CONFIGURE_SERVICES_TASK' ||
          value == 'WAITING_GENERATE_ENTITIES',
      }"
    >
      <font-awesome-icon
        v-if="value == 'WAITING_CONFIGURE_SERVICES_TASK' || value == 'WAITING_GENERATE_ENTITIES'"
        icon="cogs"
      ></font-awesome-icon>
      <font-awesome-icon v-if="value == 'WAITING_CONFIGURE_START_FORM'" icon="forward"></font-awesome-icon>
      <font-awesome-icon v-if="value == 'WAITING_CONFIGURE_USERS_TASK'" icon="user"></font-awesome-icon>
      <font-awesome-icon v-if="value == 'WAITING_CONFIGURE_PROCESS_BINDING'" icon="share"></font-awesome-icon>
      <font-awesome-icon
        v-if="value == 'WAITING_CONFIGURE_DOMAIN_ENTITY' || value == 'WAITING_PROVIDE_PROCESS_BPMN'"
        icon="file"
      ></font-awesome-icon>
      <font-awesome-icon v-if="value == 'FINISHED'" icon="check"></font-awesome-icon>
      &nbsp;<span class="badge rounded-pill" v-text="$t('akipGenerationWebApp.StatusProcess.' + value)" />
    </span>
  </div>
</template>

<script lang="ts" src="./show-akip-process-status.component.ts"></script>

<style scoped>
.badge {
  color: white;
  padding: 0.35em 0.65em;
  font-weight: bold;
  font-size: 90%;
}
</style>
