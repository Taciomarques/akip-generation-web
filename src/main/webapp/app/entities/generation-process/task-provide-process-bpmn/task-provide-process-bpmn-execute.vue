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
              <label class="form-control-label mt-3" v-text="$t('akipGenerationWebApp.taskProvideProcessBpmn.bpmn')">bpmn</label>
              <div class="form-group input-group-sm d-flex mb-2" v-if="taskContext.generationProcess.akipProcess.bpmn">
                <input type="text" class="form-control" :value="taskContext.generationProcess.akipProcess.bpmn.name" disabled />
                <button
                  type="button"
                  v-on:click="
                    openFile(
                      taskContext.generationProcess.akipProcess.bpmn.specificationFileContentType,
                      taskContext.generationProcess.akipProcess.bpmn.specificationFile
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
                      taskContext.generationProcess.akipProcess.bpmn.specificationFileContentType,
                      taskContext.generationProcess.akipProcess.bpmn.specificationFile,
                      taskContext.generationProcess.akipProcess.bpmn.name
                    )
                  "
                >
                  <font-awesome-icon icon="download"></font-awesome-icon>
                </button>
                <button type="button" v-on:click="removeAttachment()" class="btn btn-danger btn-sm pull-right">
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <div v-else class="form-group">
                <label style="width: 40%" for="file_bytes" class="btn btn-primary btn-sm">
                  <font-awesome-icon icon="upload"></font-awesome-icon> {{ $t('entity.action.addblob') }}
                </label>
                <input type="file" style="visibility: hidden" accept=".bpmn" id="file_bytes" @change="setAttachment($event)" />
              </div>
            </div>

            <div v-if="$v.taskContext.generationProcess.akipProcess.bpmn.$invalid">
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
            :disabled="$v.taskContext.generationProcess.akipProcess.bpmn.$invalid || isSaving"
            class="btn btn-success float-right"
            data-cy="complete"
          >
            <font-awesome-icon icon="check-circle"></font-awesome-icon>&nbsp;Complete
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-provide-process-bpmn-execute.component.ts"></script>
