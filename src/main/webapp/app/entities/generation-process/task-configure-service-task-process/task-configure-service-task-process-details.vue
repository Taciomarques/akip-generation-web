<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <h2 id="page-heading" data-cy="TaskInstanceHeading">
        <span v-text="$t('akipGenerationWebApp.taskInstance.details.title')" id="task-instance-heading">Task Details</span>
      </h2>
      <div v-if="taskContext.taskInstance">
        <akip-show-task-instance class="border-primary" :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <li class="list-group-item d-flex justify-content-between align-items-center">
              {{ taskContext.akipEntityServiceTask.name }}
              <show-akip-entity-type :value="taskContext.akipEntityServiceTask.type"></show-akip-entity-type>
            </li>
          </template>
        </akip-show-task-instance>
        <br />
        <div class="container">
          <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
          </button>

          <router-link
            v-if="taskContext.taskInstance.status == 'NEW' || taskContext.taskInstance.status == 'ASSIGNED'"
            :to="`/process-definition/GenerationProcess/task/TaskConfigureServiceTaskProcess/${taskContext.taskInstance.id}/execute`"
            tag="button"
            class="btn btn-primary float-right"
            data-cy="entityDetailsButton"
          >
            <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;Execute
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-configure-service-task-process-details.component.ts"></script>
