<template>
  <div>
    <div class="card" v-if="akipProcess">
      <h4 class="card-header collapse-link" v-on:click="collapse('showAkipProcess')">
        <div class="d-flex">
          <div class="p-1">
            <span class="title" v-text="$t('akipGenerationWebApp.akipProcess.detail.title')"> </span>
          </div>
          <div class="p-1 ml-3 small">
            <show-akip-process-status :value="akipProcess.status"></show-akip-process-status>
          </div>
          <div class="p-1 ml-3">
            <font-awesome-icon icon="compress-alt" v-if="collapseController.showAkipProcess"></font-awesome-icon>
            <font-awesome-icon icon="expand-alt" v-else></font-awesome-icon>
          </div>
        </div>
      </h4>
      <b-collapse v-model="collapseController.showAkipProcess" id="collapse-akip-process">
        <div class="card-body">
          <div class="row">
            <div class="col">
              <div class="form-group input-group-sm">
                <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipProcess.name')" for="process-name">Name</label>
                <input type="text" class="form-control" name="name" readonly id="process-name" data-cy="name" v-model="akipProcess.name" />
              </div>
            </div>
            <div class="col">
              <div class="form-group input-group-sm">
                <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipProcess.processBpmnId')" for="process-processBpmnId"
                  >processBpmnId</label
                >
                <input
                  type="text"
                  class="form-control"
                  name="processBpmnId"
                  readonly
                  id="process-processBpmnId"
                  data-cy="processBpmnId"
                  v-model="akipProcess.processBpmnId"
                />
              </div>
            </div>
            <div class="col">
              <div class="form-group input-group-sm">
                <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipProcess.application')" for="akip-process-application"
                  >Application Name</label
                >
                <input
                  type="text"
                  class="form-control"
                  name="application"
                  readonly
                  id="akip-process-application"
                  data-cy="application"
                  v-model="akipProcess.application.name"
                />
              </div>
            </div>
          </div>

          <div class="form-group">
            <label v-text="$t('akipGenerationWebApp.akipProcess.percentageExecuted')" />
            <div class="progress">
              <div
                class="progress-bar"
                :class="{ 'progress-bar-striped progress-bar-animated bg-warning': akipProcess.status != 'FINISHED' }"
                role="progressbar"
                :aria-valuenow="akipProcess.percentageExecuted"
                aria-valuemin="0"
                aria-valuemax="100"
                :style="'width: ' + akipProcess.percentageExecuted + '%;'"
              >
                {{ akipProcess.percentageExecuted + '%' }}
              </div>
            </div>

            <div v-if="akipProcess.attachments && akipProcess.attachments.length > 0">
              <hr />
              <div class="card">
                <h4 class="card-header collapse-link" v-on:click="collapse('showAttachments')">
                  <div class="d-flex">
                    <div class="p-1">
                      <font-awesome-icon icon="file"></font-awesome-icon>
                    </div>
                    <div class="p-1">
                      <span class="title" v-text="$t('akipGenerationWebApp.akipProcess.attachments')"> </span>
                    </div>
                    <div class="p-1 ml-3">
                      <font-awesome-icon icon="compress-alt" v-if="collapseController.showAkipProcess"></font-awesome-icon>
                      <font-awesome-icon icon="expand-alt" v-else></font-awesome-icon>
                    </div>
                  </div>
                </h4>
                <b-collapse v-model="collapseController.showAttachments" id="collapse-attachments">
                  <div class="card-body">
                    <ul class="list-group">
                      <li class="list-group-item justify-content-between align-items-center" v-for="attachment in akipProcess.attachments">
                        <div class="form-group input-group-sm d-flex mb-2">
                          <span readonly type="text" class="form-control" v-text="attachment.name" />
                          <button
                            type="button"
                            v-on:click="openFile(attachment.specificationFileContentType, attachment.specificationFile)"
                            class="btn btn-info btn-sm pull-right"
                          >
                            <font-awesome-icon icon="folder-open"></font-awesome-icon>
                          </button>
                          <button
                            class="btn btn-primary btn-sm pull-right"
                            @click="downloadFile(attachment.specificationFileContentType, attachment.specificationFile, attachment.name)"
                          >
                            <font-awesome-icon icon="download"></font-awesome-icon>
                          </button>
                        </div>
                        <span class="text-gray-700"
                          ><i
                            ><small
                              ><font-awesome-icon icon="clock"></font-awesome-icon>&nbsp;<b>{{ $t('label.created') + ': ' }}</b
                              >{{ new Date(attachment.createDateTime) }}</small
                            ></i
                          ></span
                        >
                      </li>
                    </ul>
                  </div>
                </b-collapse>
              </div>
            </div>
          </div>

          <div class="mt-3" v-if="akipEntityDomain">
            <hr />
            <show-akip-entity
              :akipEntityProp="akipEntityDomain"
              :readOnly="true"
              :typeEntity="akipEntityDomain.type"
              :applicationId="akipEntityDomain.application.id"
            ></show-akip-entity>
          </div>

          <div class="mt-3" v-if="akipEntityProcessBinding">
            <hr />
            <show-akip-entity
              :akipEntityProp="akipEntityProcessBinding"
              :readOnly="true"
              :typeEntity="akipEntityProcessBinding.type"
              :applicationId="akipEntityProcessBinding.application.id"
            ></show-akip-entity>
          </div>

          <div class="mt-3" v-if="akipEntityStartForm">
            <hr />
            <show-akip-entity
              :akipEntityProp="akipEntityStartForm"
              :readOnly="true"
              :typeEntity="akipEntityStartForm.type"
              :applicationId="akipEntityStartForm.application.id"
            ></show-akip-entity>
          </div>

          <div class="mt-3" v-for="akipEntity in akipProcess.entities.filter(akipEntity => akipEntity.type == 'USER_TASK')">
            <hr />
            <show-akip-entity
              :akipEntityProp="akipEntity"
              :readOnly="true"
              :typeEntity="akipEntity.type"
              :applicationId="akipEntity.application.id"
            ></show-akip-entity>
          </div>

          <div v-if="akipProcess.entities.filter(akipEntity => akipEntity.type == 'SERVICE_TASK').length > 0">
            <hr />
            <ul class="list-group">
              <li
                class="list-group-item d-flex justify-content-between align-items-center"
                v-for="akipEntity in akipProcess.entities.filter(akipEntity => akipEntity.type == 'SERVICE_TASK')"
              >
                {{ akipEntity.name }}
                <show-akip-entity-type :value="akipEntity.type"></show-akip-entity-type>
              </li>
            </ul>
          </div>
        </div>
      </b-collapse>
    </div>
  </div>
</template>

<script lang="ts" src="./show-akip-process.component.ts"></script>
