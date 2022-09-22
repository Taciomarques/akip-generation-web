<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="akipGenerationWebApp.generationProcessStartForm.home.createOrEditLabel"
          data-cy="GenerationProcessStartFormCreateUpdateHeading"
          v-text="$t('akipGenerationWebApp.generationProcessStartForm.home.createOrEditLabel')"
        >
          Create or edit a GenerationProcessStartForm
        </h2>
        <div v-if="generationProcess.processInstance">
          <akip-show-process-definition :processDefinition="generationProcess.processInstance.processDefinition">
            <template v-slot:body>
              <div class="row">
                <div class="col">
                  <img src="content/images/home.png" class="img-fluid img-thumbnail" />
                </div>
                <hr />
                <div class="col" v-if="generationProcess.process">
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
                      :class="{ valid: !$v.generationProcess.process.name.$invalid, invalid: $v.generationProcess.process.name.$invalid }"
                      v-model="$v.generationProcess.process.name.$model"
                    />
                  </div>
                  <div class="form-group">
                    <label
                      class="form-control-label"
                      v-text="$t('akipGenerationWebApp.generationProcessStartForm.application')"
                      for="generation-process-start-form-application"
                      >Application</label
                    >
                    <select
                      class="form-control"
                      id="generation-process-start-form-application"
                      data-cy="application"
                      name="application"
                      :class="{
                        invalid: $v.generationProcess.process.application.$invalid,
                      }"
                      v-model="generationProcess.process.application"
                    >
                      <option v-bind:value="null"></option>
                      <option
                        v-bind:value="
                          generationProcess.process.application && ApplicationOption.id === generationProcess.process.application.id
                            ? generationProcess.process.application
                            : ApplicationOption
                        "
                        v-for="ApplicationOption in Applications"
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
          <br />
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.generationProcess.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;<span>Start</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./generation-process-start-form-init.component.ts"></script>
