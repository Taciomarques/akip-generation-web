<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <h2 id="page-heading" data-cy="TaskInstanceHeading">
        <span v-text="$t('akipGenerationWebApp.taskInstance.details.title')" id="task-instance-heading">Task Details</span>
      </h2>
      <div v-if="taskContext.taskInstance">
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="card mb-3">
              <div class="card-body">
                <div v-if="taskContext.metadatasAkipEntitiesDomain && taskContext.metadatasAkipEntitiesDomain.length > 0">
                  <label class="form-control-label mt-3" v-text="$t('akipGenerationWebApp.taskGenerateEntities.metadatasDomain')"
                    >Metadata Domain Entity</label
                  >
                  <ul class="list-group">
                    <li
                      class="list-group-item justify-content-between align-items-center"
                      v-for="metadataAkipEntityDomain in taskContext.metadatasAkipEntitiesDomain"
                    >
                      <div class="form-group input-group-sm d-flex mb-2" v-if="metadataAkipEntityDomain">
                        <input type="text" class="form-control" :value="metadataAkipEntityDomain.name" disabled />
                        <button
                          type="button"
                          v-on:click="
                            openFile(metadataAkipEntityDomain.specificationFileContentType, metadataAkipEntityDomain.specificationFile)
                          "
                          class="btn btn-info btn-sm pull-right"
                        >
                          <font-awesome-icon icon="folder-open"></font-awesome-icon>
                        </button>
                        <button
                          class="btn btn-primary btn-sm pull-right"
                          @click="
                            downloadFile(
                              metadataAkipEntityDomain.specificationFileContentType,
                              metadataAkipEntityDomain.specificationFile,
                              metadataAkipEntityDomain.name
                            )
                          "
                        >
                          <font-awesome-icon icon="download"></font-awesome-icon>
                        </button>
                      </div>
                    </li>
                  </ul>
                </div>
                <div class="row">
                  <div class="col">
                    <label class="form-control-label mt-3" v-text="$t('akipGenerationWebApp.taskGenerateEntities.metadataProcessBinding')"
                      >Metadata Process Binding Entity</label
                    >
                    <div class="form-group input-group-sm d-flex mb-2" v-if="taskContext.metadataAkipEntityProcessBinding">
                      <input type="text" class="form-control" :value="taskContext.metadataAkipEntityProcessBinding.name" disabled />
                      <button
                        type="button"
                        v-on:click="
                          openFile(
                            taskContext.metadataAkipEntityProcessBinding.specificationFileContentType,
                            taskContext.metadataAkipEntityProcessBinding.specificationFile
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
                            taskContext.metadataAkipEntityProcessBinding.specificationFileContentType,
                            taskContext.metadataAkipEntityProcessBinding.specificationFile,
                            taskContext.metadataAkipEntityProcessBinding.name
                          )
                        "
                      >
                        <font-awesome-icon icon="download"></font-awesome-icon>
                      </button>
                    </div>
                  </div>
                  <div class="col">
                    <label class="form-control-label mt-3" v-text="$t('akipGenerationWebApp.taskGenerateEntities.metadataStartForm')"
                      >Metadata Start Form Entity</label
                    >
                    <div class="form-group input-group-sm d-flex mb-2" v-if="taskContext.metadataAkipEntityStartForm">
                      <input type="text" class="form-control" :value="taskContext.metadataAkipEntityStartForm.name" disabled />
                      <button
                        type="button"
                        v-on:click="
                          openFile(
                            taskContext.metadataAkipEntityStartForm.specificationFileContentType,
                            taskContext.metadataAkipEntityStartForm.specificationFile
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
                            taskContext.metadataAkipEntityStartForm.specificationFileContentType,
                            taskContext.metadataAkipEntityStartForm.specificationFile,
                            taskContext.metadataAkipEntityStartForm.name
                          )
                        "
                      >
                        <font-awesome-icon icon="download"></font-awesome-icon>
                      </button>
                    </div>
                  </div>
                </div>
                <div>
                  <label class="form-control-label mt-3" v-text="$t('akipGenerationWebApp.taskGenerateEntities.metadatasUserTasks')"
                    >Metadatas User Tasks Entity</label
                  >
                  <ul class="list-group">
                    <li
                      class="list-group-item justify-content-between align-items-center"
                      v-for="metadataAkipEntityUserTask in taskContext.metadatasAkipEntitiesUserTasks"
                    >
                      <div class="form-group input-group-sm d-flex mb-2">
                        <input type="text" class="form-control" :value="metadataAkipEntityUserTask.name" disabled />
                        <button
                          type="button"
                          v-on:click="
                            openFile(metadataAkipEntityUserTask.specificationFileContentType, metadataAkipEntityUserTask.specificationFile)
                          "
                          class="btn btn-info btn-sm pull-right"
                        >
                          <font-awesome-icon icon="folder-open"></font-awesome-icon>
                        </button>
                        <button
                          class="btn btn-primary btn-sm pull-right"
                          @click="
                            downloadFile(
                              metadataAkipEntityUserTask.specificationFileContentType,
                              metadataAkipEntityUserTask.specificationFile,
                              metadataAkipEntityUserTask.name
                            )
                          "
                        >
                          <font-awesome-icon icon="download"></font-awesome-icon>
                        </button>
                      </div>
                    </li>
                  </ul>
                </div>

                <div>
                  <label class="form-control-label mt-3" v-text="$t('akipGenerationWebApp.taskGenerateEntities.metadatasServiceTasks')"
                    >Metadatas Service Tasks Entity</label
                  >
                  <ul class="list-group">
                    <li
                      class="list-group-item justify-content-between align-items-center"
                      v-for="metadataAkipEntityServiceTask in taskContext.metadatasAkipEntitiesServiceTasks"
                    >
                      <div class="form-group input-group-sm d-flex mb-2">
                        <input type="text" class="form-control" :value="metadataAkipEntityServiceTask.name" disabled />
                        <button
                          type="button"
                          v-on:click="
                            openFile(
                              metadataAkipEntityServiceTask.specificationFileContentType,
                              metadataAkipEntityServiceTask.specificationFile
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
                              metadataAkipEntityServiceTask.specificationFileContentType,
                              metadataAkipEntityServiceTask.specificationFile,
                              metadataAkipEntityServiceTask.name
                            )
                          "
                        >
                          <font-awesome-icon icon="download"></font-awesome-icon>
                        </button>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <div class="container">
          <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
          </button>

          <router-link
            v-if="taskContext.taskInstance.status == 'NEW' || taskContext.taskInstance.status == 'ASSIGNED'"
            :to="`/process-definition/GenerationProcess/task/TaskGenerateEntities/${taskContext.taskInstance.id}/execute`"
            tag="button"
            class="btn btn-primary float-right"
            data-cy="entityDetailsButton"
          >
            <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;Execute
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-generate-entities-details.component.ts"></script>
