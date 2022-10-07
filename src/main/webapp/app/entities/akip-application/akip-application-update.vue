<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <div class="card border-primary">
          <div class="card-header">
            <h2
              id="akipGenerationWebApp.akipApplication.home.createOrEditLabel"
              data-cy="ApplicationCreateUpdateHeading"
              v-text="$t('akipGenerationWebApp.akipApplication.home.createOrEditLabel')"
            >
              Create or edit a Application
            </h2>
          </div>
          <div class="card-body">
            <div class="form-group" v-if="akipApplication.id">
              <label for="id" v-text="$t('global.field.id')">ID</label>
              <input type="text" class="form-control" id="id" name="id" v-model="akipApplication.id" readonly />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipApplication.name')" for="akipApplication-name"
                >Name</label
              >
              <input
                type="text"
                class="form-control"
                name="name"
                id="akipApplication-name"
                data-cy="name"
                :class="{ valid: !$v.akipApplication.name.$invalid, invalid: $v.akipApplication.name.$invalid }"
                v-model="$v.akipApplication.name.$model"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('akipGenerationWebApp.akipApplication.repositoryName')"
                for="akipApplication-repositoryName"
                >Repository Name</label
              >
              <input
                type="text"
                class="form-control"
                name="repositoryName"
                id="akipApplication-repositoryName"
                data-cy="repositoryName"
                :class="{ valid: !$v.akipApplication.repositoryName.$invalid, invalid: $v.akipApplication.repositoryName.$invalid }"
                v-model="$v.akipApplication.repositoryName.$model"
              />
            </div>
            <!--          <div class="form-group">-->
            <!--            <label-->
            <!--              class="form-control-label"-->
            <!--              v-text="$t('akipGenerationWebApp.akipApplication.createDate')"-->
            <!--              for="akipApplication-createDate"-->
            <!--              >Create Date</label-->
            <!--            >-->
            <!--            <b-input-group class="mb-3">-->
            <!--              <b-input-group-prepend>-->
            <!--                <b-form-datepicker-->
            <!--                  aria-controls="akipApplication-createDate"-->
            <!--                  v-model="$v.akipApplication.createDate.$model"-->
            <!--                  name="createDate"-->
            <!--                  class="form-control"-->
            <!--                  :locale="currentLanguage"-->
            <!--                  button-only-->
            <!--                  today-button-->
            <!--                  reset-button-->
            <!--                  close-button-->
            <!--                >-->
            <!--                </b-form-datepicker>-->
            <!--              </b-input-group-prepend>-->
            <!--              <b-form-input-->
            <!--                id="akipApplication-createDate"-->
            <!--                data-cy="createDate"-->
            <!--                type="text"-->
            <!--                class="form-control"-->
            <!--                name="createDate"-->
            <!--                :class="{ valid: !$v.akipApplication.createDate.$invalid, invalid: $v.akipApplication.createDate.$invalid }"-->
            <!--                v-model="$v.akipApplication.createDate.$model"-->
            <!--              />-->
            <!--            </b-input-group>-->
            <!--          </div>-->
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('akipGenerationWebApp.akipApplication.properties')"
                for="akipApplication-properties"
                >Properties</label
              >
              <input
                type="text"
                class="form-control"
                name="properties"
                id="akipApplication-properties"
                data-cy="properties"
                :class="{ valid: !$v.akipApplication.properties.$invalid, invalid: $v.akipApplication.properties.$invalid }"
                v-model="$v.akipApplication.properties.$model"
              />
            </div>
            <div v-if="$v.akipApplication.$invalid">
              <div class="alert alert-dismissible alert-danger mt-3">
                <strong>
                  <b><label v-text="$t('akipGenerationWebApp.generationProcess.checkMandatoryFields')"></label></b>
                </strong>
              </div>
            </div>
          </div>
          <div class="card-footer">
            <div class="container">
              <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
              </button>
              <button
                type="submit"
                id="save-akipEntity"
                data-cy="entityCreateSaveButton"
                :disabled="$v.akipApplication.$invalid || isSaving"
                class="btn btn-primary float-right"
              >
                <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
              </button>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./akip-application-update.component.ts"></script>
