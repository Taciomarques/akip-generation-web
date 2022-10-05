<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <div v-if="taskContext.taskInstance">
        <h2 id="page-heading" data-cy="TaskInstanceHeading">
          <span v-text="$t('akipGenerationWebApp.taskInstance.execute.title')" id="task-instance-heading">Task Execution</span>
        </h2>
        <akip-show-task-instance class="border-primary" :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <show-akip-entity
              :akipEntityProp="taskContext.akipEntityStartForm"
              @update-akip-entity="taskContext.akipEntityStartForm = $event"
              @is-akip-entity-invalid="isAkipEntityInvalid = $event"
              :applicationId="taskContext.generationProcess.akipProcess.application.id"
              :typeEntity="'START_FORM'"
            ></show-akip-entity>

            <div v-if="isAkipEntityInvalid">
              <div class="alert alert-dismissible alert-active border-danger mt-3">
                <strong>
                  <b><label class="text-danger" v-text="$t('akipGenerationWebApp.generationProcess.checkMandatoryFields')"></label></b>
                </strong>
              </div>
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <div class="container">
          <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
          </button>
          <button
            type="submit"
            v-on:click.prevent="complete()"
            :disabled="isAkipEntityInvalid || isSaving"
            class="btn btn-success float-right"
            data-cy="complete"
          >
            <font-awesome-icon icon="cogs"></font-awesome-icon>&nbsp;<span
              v-text="$t('akipGenerationWebApp.taskGenerateStartFormProcess.generateStartFormProcessAndContinueProcess')"
            />&nbsp;<font-awesome-icon icon="arrow-right"></font-awesome-icon>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-generate-start-form-process-execute.component.ts"></script>
