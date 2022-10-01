<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <div class="card">
          <div class="card-header">
            <h2
              id="akipGenerationWebApp.generationProcessStartForm.home.title"
              data-cy="GenerationProcessStartFormTitleHeading"
              v-text="$t('akipGenerationWebApp.generationProcessStartForm.home.title')"
            >
              Create or edit a GenerationProcessStartForm
            </h2>
          </div>
          <div class="card-body" v-if="generationProcess.processInstance">
            <akip-show-process-definition :processDefinition="generationProcess.processInstance.processDefinition">
              <template v-slot:body>
                <div class="row">
                  <div class="col">
                    <img src="content/images/home.png" class="img-fluid img-thumbnail" />
                  </div>
                  <hr />
                  <div class="col" v-if="generationProcess.akipProcess">
                    <div class="form-group">
                      <label
                        class="form-control-label"
                        v-text="$t('akipGenerationWebApp.generationProcessStartForm.name')"
                        for="generation-process-start-form-name"
                        >Name</label
                      >
                      <input
                        type="text"
                        class="form-control"
                        name="name"
                        id="generation-process-start-form-name"
                        data-cy="name"
                        :class="{
                          valid: !$v.generationProcess.akipProcess.name.$invalid,
                          invalid: $v.generationProcess.akipProcess.name.$invalid,
                        }"
                        v-model="$v.generationProcess.akipProcess.name.$model"
                      />
                    </div>
                    <div class="form-group">
                      <label
                        class="form-control-label"
                        v-text="$t('akipGenerationWebApp.generationProcessStartForm.application')"
                        for="generation-process-start-form-akipApplication"
                        >Application</label
                      >
                      <select
                        class="form-control"
                        id="generation-process-start-form-akipApplication"
                        data-cy="akipApplication"
                        name="akipApplication"
                        :class="{
                          invalid: $v.generationProcess.akipProcess.application.$invalid,
                        }"
                        v-model="generationProcess.akipProcess.application"
                      >
                        <option
                          v-bind:value="
                            generationProcess.akipProcess.application &&
                            ApplicationOption.id === generationProcess.akipProcess.application.id
                              ? generationProcess.akipProcess.application
                              : ApplicationOption
                          "
                          v-for="ApplicationOption in akipApplications"
                          :key="ApplicationOption.id"
                        >
                          {{ ApplicationOption.name }}
                        </option>
                      </select>
                    </div>
                  </div>
                </div>
              </template>
            </akip-show-process-definition>
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
                :disabled="$v.generationProcess.$invalid || isSaving"
                class="btn btn-primary float-right"
              >
                <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;<span>Start</span>
              </button>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./generation-process-start-form-init.component.ts"></script>
