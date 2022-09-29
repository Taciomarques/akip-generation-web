<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <div v-if="taskContext.taskInstance">
        <h2 id="page-heading" data-cy="TaskInstanceHeading">
          <span v-text="$t('akipGenerationWebApp.taskInstance.execute.title')" id="task-instance-heading">Task Execution</span>
        </h2>
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <show-akip-entity
              :akipEntity="taskContext.entity"
              @update-akip-entity="taskContext.entity = $event"
              @is-akip-entity-invalid="isAkipEntityInvalid = $event"
              :applicationId="taskContext.generationProcess.akipProcess.application.id"
              :typeEntity="'DOMAIN'"
            ></show-akip-entity>
          </template>
        </akip-show-task-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <button
          type="submit"
          v-on:click.prevent="complete('YES')"
          :disabled="isAkipEntityInvalid || isSaving"
          class="btn btn-success"
          data-cy="complete"
        >
          <font-awesome-icon icon="cogs"></font-awesome-icon>&nbsp;<span
            v-text="$t('akipGenerationWebApp.taskGenerateDomainEntity.generateDomainEntityAndGenerateOtherDomainEntity')"
          />&nbsp;<font-awesome-icon icon="cogs"></font-awesome-icon>
        </button>

        <button
          type="submit"
          v-on:click.prevent="complete('NO')"
          :disabled="isAkipEntityInvalid || isSaving"
          class="btn btn-success"
          data-cy="complete"
        >
          <font-awesome-icon icon="cogs"></font-awesome-icon>&nbsp;<span
            v-text="$t('akipGenerationWebApp.taskGenerateDomainEntity.generateDomainEntityAndContinueProcess')"
          />&nbsp;<font-awesome-icon icon="arrow-right"></font-awesome-icon>
        </button>
        <button
          type="submit"
          v-on:click.prevent="complete('SKIP')"
          class="btn btn-warning"
          :disabled="isAkipEntityInvalid || isSaving"
          data-cy="entityDetailsBackButton"
        >
          <span
            v-text="$t('akipGenerationWebApp.taskGenerateDomainEntity.skipGenerateDomainEntityAndContinueProcess')"
          />&nbsp;<font-awesome-icon icon="arrow-right"></font-awesome-icon>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-generate-domain-entity-execute.component.ts"></script>
