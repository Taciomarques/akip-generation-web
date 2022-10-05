<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <h2 id="page-heading" data-cy="TaskInstanceHeading">
        <span v-text="$t('akipGenerationWebApp.taskInstance.details.title')" id="task-instance-heading">Task Details</span>
      </h2>
      <div v-if="taskContext.taskInstance">
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
              </div>
              <div v-else>
                <label for="file_bytes" class="btn btn-primary btn-sm">
                  <font-awesome-icon icon="upload"></font-awesome-icon> {{ $t('entity.action.addblob') }}
                </label>
                <input type="file" style="visibility: hidden" accept=".bpmn" id="file_bytes" readonly />
              </div>
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>

        <router-link
          v-if="taskContext.taskInstance.status == 'NEW' || taskContext.taskInstance.status == 'ASSIGNED'"
          :to="`/process-definition/GenerationProcess/task/TaskProvideProcessBpmn/${taskContext.taskInstance.id}/execute`"
          tag="button"
          class="btn btn-primary"
          data-cy="entityDetailsButton"
        >
          <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;Execute
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-provide-process-bpmn-details.component.ts"></script>
