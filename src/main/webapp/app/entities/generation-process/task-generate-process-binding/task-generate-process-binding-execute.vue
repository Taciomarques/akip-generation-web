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
            <div class="form-group input-group-sm">
              <label class="form-control-label" v-text="$t('akipGenerationWebApp.generationProcess.domainEntity')" for="domainEntity"
                >domainEntity</label
              >
              <select
                id="domainEntity"
                class="form-control"
                @change="setProcessBindingFieldsAndRelationshipsOfAkipEntityDomain()"
                :class="{ invalid: $v.akipEntityDomain.$invalid }"
                v-model="akipEntityDomain"
              >
                <option v-for="akipEntity in taskContext.akipEntitiesDomain" :value="akipEntity" v-text="akipEntity.name"></option>
              </select>
            </div>
            <show-akip-entity
              :akipEntityProp="taskContext.akipEntityProcessBinding"
              @update-akip-entity="taskContext.akipEntityProcessBinding = $event"
              @is-akip-entity-invalid="isAkipEntityInvalid = $event"
              :applicationId="taskContext.generationProcess.akipProcess.application.id"
              :typeEntity="'PROCESS_BINDING'"
            ></show-akip-entity>

            <div v-if="$v.akipEntityDomain.$invalid || isAkipEntityInvalid">
              <div class="alert alert-dismissible alert-danger mt-3">
                <strong>
                  <b><label v-text="$t('akipGenerationWebApp.generationProcess.checkMandatoryFields')"></label></b>
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
            :disabled="isAkipEntityInvalid || $v.akipEntityDomain.$invalid || isSaving"
            class="btn btn-success float-right"
            data-cy="complete"
          >
            <font-awesome-icon icon="cogs"></font-awesome-icon>&nbsp;<span
              v-text="$t('akipGenerationWebApp.taskGenerateProcessBinding.generateProcessBindingAndContinueProcess')"
            />&nbsp;<font-awesome-icon icon="arrow-right"></font-awesome-icon>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-generate-process-binding-execute.component.ts"></script>
