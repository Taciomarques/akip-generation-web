/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EntidadeDetailComponent from '../../../../../../main/webapp/app/entities/akip-entity/akip-entity-details.vue';
import EntidadeClass from '../../../../../../main/webapp/app/entities/akip-entity/akip-entity-details.component';
import AkipEntityService from '../../../../../../main/webapp/app/entities/akip-entity/akip-entity.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Entidade Management Detail Component', () => {
    let wrapper: Wrapper<EntidadeClass>;
    let comp: EntidadeClass;
    let entidadeServiceStub: SinonStubbedInstance<AkipEntityService>;

    beforeEach(() => {
      entidadeServiceStub = sinon.createStubInstance<AkipEntityService>(AkipEntityService);

      wrapper = shallowMount<EntidadeClass>(EntidadeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { akipEntityService: () => entidadeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEntidade = { id: 123 };
        entidadeServiceStub.find.resolves(foundEntidade);

        // WHEN
        comp.retrieveEntidade(123);
        await comp.$nextTick();

        // THEN
        expect(comp.entidade).toBe(foundEntidade);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEntidade = { id: 123 };
        entidadeServiceStub.find.resolves(foundEntidade);

        // WHEN
        comp.beforeRouteEnter({ params: { entidadeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.entidade).toBe(foundEntidade);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
