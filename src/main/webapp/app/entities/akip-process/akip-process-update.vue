<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="akipGenerationWebApp.process.home.createOrEditLabel"
          data-cy="ProcessCreateUpdateHeading"
          v-text="$t('akipGenerationWebApp.akipProcess.home.createOrEditLabel')"
        >
          Create or edit a Process
        </h2>
        <div>
          <div class="form-group" v-if="akipProcess.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="akipProcess.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipProcess.name')" for="process-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="process-name"
              data-cy="name"
              :class="{ valid: !$v.akipProcess.name.$invalid, invalid: $v.akipProcess.name.$invalid }"
              v-model="$v.akipProcess.name.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('akipGenerationWebApp.akipProcess.percentageExecuted')"
              for="process-percentageExecuted"
              >Percentage Executed</label
            >
            <input
              type="number"
              class="form-control"
              name="percentageExecuted"
              id="process-percentageExecuted"
              data-cy="percentageExecuted"
              :class="{ valid: !$v.akipProcess.percentageExecuted.$invalid, invalid: $v.akipProcess.percentageExecuted.$invalid }"
              v-model.number="$v.akipProcess.percentageExecuted.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipProcess.status')" for="process-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.akipProcess.status.$invalid, invalid: $v.akipProcess.status.$invalid }"
              v-model="$v.akipProcess.status.$model"
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
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipProcess.application')" for="process-akipApplication"
              >Application</label
            >
            <select
              class="form-control"
              id="process-akipApplication"
              data-cy="akipApplication"
              name="akipApplication"
              v-model="akipProcess.application"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  akipProcess.application && applicationOption.id === akipProcess.application.id
                    ? akipProcess.application
                    : applicationOption
                "
                v-for="applicationOption in akipApplications"
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
            id="save-akipEntity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.akipProcess.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./akip-process-update.component.ts"></script>
