<template>
  <div>
    <div class="card mb-3" v-if="akipEntity">
      <h4 class="card-header collapse-link" v-on:click="collapse('showAkipEntity')">
        <div class="d-flex">
          <div class="p-1">
            <span class="title" v-text="$t('akipGenerationWebApp.akipEntity.detail.title')"></span>
          </div>
          <div class="ml-3 small">
            <show-akip-entity-type :value="typeEntity"></show-akip-entity-type>
          </div>
          <div class="p-1 ml-3">
            <font-awesome-icon icon="compress-alt" v-if="collapseController.showAkipEntity"></font-awesome-icon>
            <font-awesome-icon icon="expand-alt" v-else></font-awesome-icon>
          </div>
        </div>
      </h4>
      <b-collapse v-model="collapseController.showAkipEntity" id="collapse-akip-entity">
        <div class="card-body">
          <div class="form-group input-group-sm">
            <label class="form-control-label" v-text="$t('akipGenerationWebApp.akipEntity.name')" for="akipEntity-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="akipEntity-name"
              :readonly="readOnly"
              data-cy="name"
              :class="{ invalid: $v.akipEntity.name.$invalid && !readOnly }"
              v-model="$v.akipEntity.name.$model"
            />
          </div>
          <div class="card bg-light">
            <div class="card-body">
              <div class="d-flex justify-content-between">
                <b><span class="card-title" v-text="$t('akipGenerationWebApp.akipEntity.fields')"></span></b>
                <button v-if="!readOnly" v-on:click="addField()" class="btn btn-primary" data-cy="addField">
                  <font-awesome-icon icon="plus"></font-awesome-icon>&nbsp;<span v-text="$t('akipGenerationWebApp.akipField.addField')" />
                </button>
              </div>

              <div class="mt-3" v-for="(field, index) in akipEntity.fields" :key="'field' + index">
                <show-akip-field
                  :index="index"
                  :readOnly="readOnly"
                  :typeEntity="typeEntity"
                  :akipFieldProp="field"
                  :removeFunction="removeField"
                  @is-akip-field-invalid="updateFieldsInvalid(index, $event)"
                ></show-akip-field>
              </div>
            </div>
          </div>

          <div class="card bg-light mt-3 mb-2">
            <div class="card-body">
              <div class="d-flex justify-content-between">
                <b><span class="card-title" v-text="$t('akipGenerationWebApp.akipEntity.relationships')"></span></b>
                <button
                  v-if="!readOnly && otherAkipEntities && otherAkipEntities.length > 0"
                  v-on:click="addRelationship()"
                  class="btn btn-primary"
                  data-cy="addRelationship"
                >
                  <font-awesome-icon icon="plus"></font-awesome-icon>&nbsp;<span
                    v-text="$t('akipGenerationWebApp.akipRelationship.addRelationship')"
                  />
                </button>
              </div>
              <div v-if="(!otherAkipEntities || otherAkipEntities == 0) && !readOnly">
                <div class="alert alert-warning mt-2">
                  <span v-text="$t('akipGenerationWebApp.akipEntity.home.notFound')">No entities found</span>
                </div>
              </div>
              <div class="mt-2" v-for="(relationship, index) in akipEntity.relationships" :key="'relationship' + index">
                <show-akip-relationship
                  :index="index"
                  :readOnly="readOnly"
                  :akipRelationshipProp="relationship"
                  :otherAkipEntitiesProp="otherAkipEntities"
                  :removeFunction="removeRelationship"
                  @is-akip-relationship-invalid="updateRelationshipsInvalid(index, $event)"
                ></show-akip-relationship>
              </div>
            </div>
          </div>
        </div>
      </b-collapse>
    </div>
  </div>
</template>

<script lang="ts" src="./show-akip-entity.component.ts"></script>
