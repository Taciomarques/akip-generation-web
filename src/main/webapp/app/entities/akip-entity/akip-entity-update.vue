<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="akipGenerationWebApp.akipEntity.home.createOrEditLabel"
          data-cy="EntidadeCreateUpdateHeading"
          v-text="$t('akipGenerationWebApp.akipEntity.home.createOrEditLabel')"
        >
          Create or edit a Entidade
        </h2>
        <div>
          <div class="form-group" v-if="akipEntity.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="akipEntity.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipEntity.name')" for="akipEntity-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="akipEntity-name"
              data-cy="name"
              :class="{ valid: !$v.akipEntity.name.$invalid, invalid: $v.akipEntity.name.$invalid }"
              v-model="$v.akipEntity.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipEntity.fields')" for="akipEntity-fields">Fields</label>
            <input
              type="text"
              class="form-control"
              name="fields"
              id="akipEntity-fields"
              data-cy="fields"
              :class="{ valid: !$v.akipEntity.fields.$invalid, invalid: $v.akipEntity.fields.$invalid }"
              v-model="$v.akipEntity.fields.$model"
            />
          </div>
          <!--          <div class="form-group">-->
          <!--            <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipEntity.relationships')" for="akipEntity-relationships"-->
          <!--              >Relationships</label-->
          <!--            >-->
          <!--            <input-->
          <!--              type="text"-->
          <!--              class="form-control"-->
          <!--              name="relationships"-->
          <!--              id="akipEntity-relationships"-->
          <!--              data-cy="relationships"-->
          <!--              :class="{ valid: !$v.akipEntity.relationships.$invalid, invalid: $v.akipEntity.relationships.$invalid }"-->
          <!--              v-model="$v.akipEntity.relationships.$model"-->
          <!--            />-->
          <!--          </div>-->
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipEntity.type')" for="akipEntity-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.akipEntity.type.$invalid, invalid: $v.akipEntity.type.$invalid }"
              v-model="$v.akipEntity.type.$model"
              id="akipEntity-type"
              data-cy="type"
            >
              <option value="DOMAIN" v-bind:label="$t('akipGenerationWebApp.TypeEntity.DOMAIN')">DOMAIN</option>
              <option value="PROCESS_BINDING" v-bind:label="$t('akipGenerationWebApp.TypeEntity.PROCESS_BINDING')">PROCESS_BINDING</option>
              <option value="START_FORM" v-bind:label="$t('akipGenerationWebApp.TypeEntity.START_FORM')">START_FORM</option>
              <option value="USER_TASK" v-bind:label="$t('akipGenerationWebApp.TypeEntity.USER_TASK')">USER_TASK</option>
              <option value="SERVICE_TASK" v-bind:label="$t('akipGenerationWebApp.TypeEntity.SERVICE_TASK')">SERVICE_TASK</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipEntity.process')" for="akipEntity-process"
              >Process</label
            >
            <select class="form-control" id="akipEntity-process" data-cy="process" name="process" v-model="akipEntity.process">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="akipEntity.process && processOption.id === akipEntity.process.id ? akipEntity.process : processOption"
                v-for="processOption in akipProcesses"
                :key="processOption.id"
              >
                {{ processOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipEntity.application')" for="akipEntity-akipApplication"
              >Application</label
            >
            <select
              class="form-control"
              id="akipEntity-akipApplication"
              data-cy="akipApplication"
              name="akipApplication"
              v-model="akipEntity.application"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  akipEntity.application && applicationOption.id === akipEntity.application.id ? akipEntity.application : applicationOption
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
            :disabled="$v.akipEntity.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./akip-entity-update.component.ts"></script>
