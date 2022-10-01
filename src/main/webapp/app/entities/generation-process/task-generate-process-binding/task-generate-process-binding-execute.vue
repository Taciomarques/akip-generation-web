<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <div v-if="taskContext.taskInstance">
        <h2 id="page-heading" data-cy="TaskInstanceHeading">
          <span v-text="$t('akipGenerationWebApp.taskInstance.execute.title')" id="task-instance-heading">Task Execution</span>
        </h2>
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group input-group-sm">
              <label class="form-control-label" v-text="$t('akipGenerationWebApp.generationProcess.domainEntity')" for="domainEntity"
                >domainEntity</label
              >
              <select id="domainEntity" class="form-control" :class="{ invalid: $v.domainEntity.$invalid }" v-model="domainEntity">
                <option v-for="entity in taskContext.entities" :value="entity" v-text="entity.name"></option>
              </select>
            </div>
            <show-akip-entity
              :akipEntityProp="taskContext.entity"
              @update-akip-entity="taskContext.entity = $event"
              @is-akip-entity-invalid="isAkipEntityInvalid = $event"
              :applicationId="taskContext.generationProcess.akipProcess.application.id"
              :typeEntity="'PROCESS_BINDING'"
            ></show-akip-entity>
          </template>
        </akip-show-task-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <button
          type="submit"
          v-on:click.prevent="complete()"
          :disabled="isAkipEntityInvalid || isSaving"
          class="btn btn-success"
          data-cy="complete"
        >
          <font-awesome-icon icon="cogs"></font-awesome-icon>&nbsp;<span
            v-text="$t('akipGenerationWebApp.taskGenerateProcessBinding.generateProcessBindingAndContinueProcess')"
          />&nbsp;<font-awesome-icon icon="arrow-right"></font-awesome-icon>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-generate-process-binding-execute.component.ts"></script>
