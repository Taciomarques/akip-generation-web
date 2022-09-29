<template>
  <div>
    <div class="card mb-3">
      <div class="card-header" v-if="typeEntity">
        <font-awesome-icon v-if="typeEntity == 'PROCESS_BINDING'" icon="share"></font-awesome-icon>
        <font-awesome-icon v-if="typeEntity == 'START_FORM'" icon="forward"></font-awesome-icon>
        <font-awesome-icon v-if="typeEntity == 'USER_TASK'" icon="user"></font-awesome-icon>
        <font-awesome-icon v-if="typeEntity == 'SERVICE_TASK'" icon="cogs"></font-awesome-icon>
        <font-awesome-icon v-if="typeEntity == 'DOMAIN'" icon="file"></font-awesome-icon>&nbsp;
        <span v-text="$t('akipGenerationWebApp.TypeEntity.' + typeEntity)" />
      </div>
      <div class="card-body">
        <div class="form-group">
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
        <div class="card">
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

        <div class="card mt-3 mb-2">
          <div class="card-body">
            <div class="d-flex justify-content-between">
              <b><span class="card-title" v-text="$t('akipGenerationWebApp.akipEntity.relationships')"></span></b>
              <button v-if="!readOnly" v-on:click="addRelationship()" class="btn btn-primary" data-cy="addRelationship">
                <font-awesome-icon icon="plus"></font-awesome-icon>&nbsp;<span
                  v-text="$t('akipGenerationWebApp.akipRelationship.addRelationship')"
                />
              </button>
            </div>

            <div class="mt-2" v-for="(relationship, index) in akipEntity.relationships" :key="'relationship' + index">
              <show-akip-relationship
                :index="index"
                :readOnly="readOnly"
                :akipRelationshipProp="relationship"
                :otherAkipEntities="otherAkipEntities"
                :removeFunction="removeRelationship"
                @is-akip-relationship-invalid="updateRelationshipsInvalid(index, $event)"
              ></show-akip-relationship>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./show-akip-entity.component.ts"></script>
