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
            <div class="card">
              <div class="card-body">
                <div class="form-group input-group-sm">
                  <label class="form-control-label" v-text="$t('akipGenerationWebApp.generationProcess.domainEntity')" for="domainEntity"
                    >domainEntity</label
                  >
                  <select
                    id="domainEntity"
                    class="form-control"
                    :class="{ invalid: $v.akipEntityDomain.$invalid }"
                    v-model="akipEntityDomain"
                  >
                    <option v-for="akipEntity in taskContext.akipEntitiesDomain" :value="akipEntity" v-text="akipEntity.name"></option>
                  </select>
                </div>

                <show-akip-fields-select
                  :akipFields="akipEntityDomain.fields"
                  @akip-fields-selecteds="taskContext.akipEntityProcessBinding.fields = $event"
                ></show-akip-fields-select>

                <show-akip-relationships-select
                  class="mt-3"
                  :akipRelationships="akipEntityDomain.relationships"
                  @akip-relationships-selecteds="taskContext.akipEntityProcessBinding.relationships = $event"
                ></show-akip-relationships-select>
              </div>
            </div>

            <show-akip-entity
              class="mt-3"
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
              v-text="$t('akipGenerationWebApp.taskConfigureProcessBinding.configureProcessBindingAndContinueProcess')"
            />&nbsp;<font-awesome-icon icon="arrow-right"></font-awesome-icon>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-configure-process-binding-execute.component.ts"></script>
