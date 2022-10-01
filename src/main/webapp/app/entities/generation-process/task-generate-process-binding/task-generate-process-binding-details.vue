<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <h2 id="page-heading" data-cy="TaskInstanceHeading">
        <span v-text="$t('akipGenerationWebApp.taskInstance.details.title')" id="task-instance-heading">Task Details</span>
      </h2>
      <div v-if="taskContext.taskInstance">
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group input-group-sm">
              <label class="form-control-label" v-text="$t('akipGenerationWebApp.generationProcess.domainEntity')" for="domainEntity"
                >domainEntity</label
              >
              <select readonly id="domainEntity" class="form-control" v-model="getDomainEntityInAkipProcess">
                <option v-for="entity in taskContext.entities" :value="entity" v-text="entity.name"></option>
              </select>
            </div>
            <show-akip-entity
              :akipEntityProp="getProcessBindingInAkipProcess"
              :readOnly="true"
              :typeEntity="'PROCESS_BINDING'"
            ></show-akip-entity>
          </template>
        </akip-show-task-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>

        <router-link
          v-if="taskContext.taskInstance.status == 'NEW' || taskContext.taskInstance.status == 'ASSIGNED'"
          :to="`/process-definition/GenerationProcess/task/TaskGenerateProcessBinding/${taskContext.taskInstance.id}/execute`"
          tag="button"
          class="btn btn-primary"
          data-cy="entityDetailsButton"
        >
          <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;Execute
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-generate-process-binding-details.component.ts"></script>
