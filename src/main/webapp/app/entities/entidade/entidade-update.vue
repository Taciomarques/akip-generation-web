<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="akipGenerationWebApp.entidade.home.createOrEditLabel"
          data-cy="EntidadeCreateUpdateHeading"
          v-text="$t('akipGenerationWebApp.entidade.home.createOrEditLabel')"
        >
          Create or edit a Entidade
        </h2>
        <div>
          <div class="form-group" v-if="entidade.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="entidade.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.entidade.name')" for="entidade-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="entidade-name"
              data-cy="name"
              :class="{ valid: !$v.entidade.name.$invalid, invalid: $v.entidade.name.$invalid }"
              v-model="$v.entidade.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.entidade.fields')" for="entidade-fields">Fields</label>
            <input
              type="text"
              class="form-control"
              name="fields"
              id="entidade-fields"
              data-cy="fields"
              :class="{ valid: !$v.entidade.fields.$invalid, invalid: $v.entidade.fields.$invalid }"
              v-model="$v.entidade.fields.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.entidade.relations')" for="entidade-relations"
              >Relations</label
            >
            <input
              type="text"
              class="form-control"
              name="relations"
              id="entidade-relations"
              data-cy="relations"
              :class="{ valid: !$v.entidade.relations.$invalid, invalid: $v.entidade.relations.$invalid }"
              v-model="$v.entidade.relations.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.entidade.type')" for="entidade-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.entidade.type.$invalid, invalid: $v.entidade.type.$invalid }"
              v-model="$v.entidade.type.$model"
              id="entidade-type"
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
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.entidade.process')" for="entidade-process">Process</label>
            <select class="form-control" id="entidade-process" data-cy="process" name="process" v-model="entidade.process">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="entidade.process && processOption.id === entidade.process.id ? entidade.process : processOption"
                v-for="processOption in processes"
                :key="processOption.id"
              >
                {{ processOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.entidade.application')" for="entidade-application"
              >Application</label
            >
            <select class="form-control" id="entidade-application" data-cy="application" name="application" v-model="entidade.application">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  entidade.application && applicationOption.id === entidade.application.id ? entidade.application : applicationOption
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
            :disabled="$v.entidade.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./entidade-update.component.ts"></script>
