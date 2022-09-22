<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="akipGenerationWebApp.process.home.createOrEditLabel"
          data-cy="ProcessCreateUpdateHeading"
          v-text="$t('akipGenerationWebApp.process.home.createOrEditLabel')"
        >
          Create or edit a Process
        </h2>
        <div>
          <div class="form-group" v-if="process.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="process.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.process.name')" for="process-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="process-name"
              data-cy="name"
              :class="{ valid: !$v.process.name.$invalid, invalid: $v.process.name.$invalid }"
              v-model="$v.process.name.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('akipGenerationWebApp.process.percentageExecuted')"
              for="process-percentageExecuted"
              >Percentage Executed</label
            >
            <input
              type="number"
              class="form-control"
              name="percentageExecuted"
              id="process-percentageExecuted"
              data-cy="percentageExecuted"
              :class="{ valid: !$v.process.percentageExecuted.$invalid, invalid: $v.process.percentageExecuted.$invalid }"
              v-model.number="$v.process.percentageExecuted.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.process.status')" for="process-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.process.status.$invalid, invalid: $v.process.status.$invalid }"
              v-model="$v.process.status.$model"
              id="process-status"
              data-cy="status"
            >
              <option
                value="WAITING_PROVIDE_DOMAIN_ENTITIES"
                v-bind:label="$t('akipGenerationWebApp.StatusProcess.WAITING_PROVIDE_DOMAIN_ENTITIES')"
              >
                WAITING_PROVIDE_DOMAIN_ENTITIES
              </option>
              <option
                value="WAITING_PROVIDE_PROCESS_BINDING"
                v-bind:label="$t('akipGenerationWebApp.StatusProcess.WAITING_PROVIDE_PROCESS_BINDING')"
              >
                WAITING_PROVIDE_PROCESS_BINDING
              </option>
              <option
                value="WAITING_PROVIDE_PROCESS_BPMN"
                v-bind:label="$t('akipGenerationWebApp.StatusProcess.WAITING_PROVIDE_PROCESS_BPMN')"
              >
                WAITING_PROVIDE_PROCESS_BPMN
              </option>
              <option
                value="WAITING_PROVIDE_START_FORM_AND_PROCESS_TASKS"
                v-bind:label="$t('akipGenerationWebApp.StatusProcess.WAITING_PROVIDE_START_FORM_AND_PROCESS_TASKS')"
              >
                WAITING_PROVIDE_START_FORM_AND_PROCESS_TASKS
              </option>
              <option value="FINISHED" v-bind:label="$t('akipGenerationWebApp.StatusProcess.FINISHED')">FINISHED</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.process.application')" for="process-application"
              >Application</label
            >
            <select class="form-control" id="process-application" data-cy="application" name="application" v-model="process.application">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  process.application && applicationOption.id === process.application.id ? process.application : applicationOption
                "
                v-for="applicationOption in applications"
                :key="applicationOption.id"
              >
                {{ applicationOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.process.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./process-update.component.ts"></script>
