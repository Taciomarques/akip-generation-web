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

            <div>
              <label class="form-control-label mt-3" v-text="$t('akipGenerationWebApp.taskGenerateEntities.metadataDomain')"
                >Metadata Domain Entity</label
              >
              <div class="form-group input-group-sm d-flex mb-2" v-if="taskContext.metadataAkipEntityDomain">
                <input type="text" class="form-control" :value="taskContext.metadataAkipEntityDomain.name" disabled />
                <button
                  type="button"
                  v-on:click="
                    openFile(
                      taskContext.metadataAkipEntityDomain.specificationFileContentType,
                      taskContext.metadataAkipEntityDomain.specificationFile
                    )
                  "
                  class="btn btn-info btn-sm pull-right"
                >
                  <font-awesome-icon icon="folder-open"></font-awesome-icon>
                </button>
                <button
                  class="btn btn-primary btn-sm pull-right"
                  @click="
                    downloadFile(
                      taskContext.metadataAkipEntityDomain.specificationFileContentType,
                      taskContext.metadataAkipEntityDomain.specificationFile,
                      taskContext.metadataAkipEntityDomain.name
                    )
                  "
                >
                  <font-awesome-icon icon="download"></font-awesome-icon>
                </button>
              </div>
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <div class="container">
          <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
          </button>
          <button type="submit" v-on:click.prevent="complete()" :disabled="isSaving" class="btn btn-success float-right" data-cy="complete">
            <font-awesome-icon icon="check-circle"></font-awesome-icon>&nbsp;Complete
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-generate-entities-execute.component.ts"></script>
