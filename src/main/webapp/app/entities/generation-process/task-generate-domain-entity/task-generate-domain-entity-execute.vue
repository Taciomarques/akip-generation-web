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
            <show-akip-entity
              :akipEntityProp="taskContext.akipEntityDomain"
              @update-akip-entity="taskContext.akipEntityDomain = $event"
              @is-akip-entity-invalid="isAkipEntityInvalid = $event"
              :applicationId="taskContext.generationProcess.akipProcess.application.id"
              :typeEntity="'DOMAIN'"
            ></show-akip-entity>
            <div v-if="isAkipEntityInvalid">
              <div class="alert alert-dismissible alert-danger mt-3">
                <strong>
                  <b><label v-text="$t('akipGenerationWebApp.generationProcess.checkMandatoryFields')"></label></b>
                </strong>
              </div>
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <div class="container"></div>
        <button
          type="submit"
          v-on:click.prevent="previousState()"
          class="btn btn-info float-lg-left mr-3"
          data-cy="entityDetailsBackButton"
        >
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <button
          type="submit"
          v-on:click.prevent="complete('YES')"
          :disabled="isAkipEntityInvalid || isSaving"
          class="btn btn-success float-left"
          data-cy="complete"
        >
          <font-awesome-icon icon="cogs"></font-awesome-icon>&nbsp;<span
            v-text="$t('akipGenerationWebApp.taskGenerateDomainEntity.generateDomainEntityAndGenerateOtherDomainEntity')"
          />&nbsp;<font-awesome-icon icon="cogs"></font-awesome-icon>
        </button>

        <button
          type="submit"
          v-on:click.prevent="complete('SKIP')"
          class="btn btn-warning float-right ml-3"
          :disabled="isSaving"
          data-cy="entityDetailsBackButton"
        >
          <span
            v-text="$t('akipGenerationWebApp.taskGenerateDomainEntity.skipGenerateDomainEntityAndContinueProcess')"
          />&nbsp;<font-awesome-icon icon="arrow-right"></font-awesome-icon>
        </button>

        <button
          type="submit"
          v-on:click.prevent="complete('NO')"
          :disabled="isAkipEntityInvalid || isSaving"
          class="btn btn-success float-right"
          data-cy="complete"
        >
          <font-awesome-icon icon="cogs"></font-awesome-icon>&nbsp;<span
            v-text="$t('akipGenerationWebApp.taskGenerateDomainEntity.generateDomainEntityAndContinueProcess')"
          />&nbsp;<font-awesome-icon icon="arrow-right"></font-awesome-icon>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-generate-domain-entity-execute.component.ts"></script>
