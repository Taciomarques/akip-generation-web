<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="akipGenerationWebApp.application.home.createOrEditLabel"
          data-cy="ApplicationCreateUpdateHeading"
          v-text="$t('akipGenerationWebApp.application.home.createOrEditLabel')"
        >
          Create or edit a Application
        </h2>
        <div>
          <div class="form-group" v-if="application.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="application.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.application.name')" for="application-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="application-name"
              data-cy="name"
              :class="{ valid: !$v.application.name.$invalid, invalid: $v.application.name.$invalid }"
              v-model="$v.application.name.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('akipGenerationWebApp.application.repositoryName')"
              for="application-repositoryName"
              >Repository Name</label
            >
            <input
              type="text"
              class="form-control"
              name="repositoryName"
              id="application-repositoryName"
              data-cy="repositoryName"
              :class="{ valid: !$v.application.repositoryName.$invalid, invalid: $v.application.repositoryName.$invalid }"
              v-model="$v.application.repositoryName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.application.createDate')" for="application-createDate"
              >Create Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="application-createDate"
                  v-model="$v.application.createDate.$model"
                  name="createDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="application-createDate"
                data-cy="createDate"
                type="text"
                class="form-control"
                name="createDate"
                :class="{ valid: !$v.application.createDate.$invalid, invalid: $v.application.createDate.$invalid }"
                v-model="$v.application.createDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.application.properties')" for="application-properties"
              >Properties</label
            >
            <input
              type="text"
              class="form-control"
              name="properties"
              id="application-properties"
              data-cy="properties"
              :class="{ valid: !$v.application.properties.$invalid, invalid: $v.application.properties.$invalid }"
              v-model="$v.application.properties.$model"
            />
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
            :disabled="$v.application.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./application-update.component.ts"></script>
