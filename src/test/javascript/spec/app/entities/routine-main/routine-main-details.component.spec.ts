/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RoutineMainDetailComponent from '@/entities/routine-main/routine-main-details.vue';
import RoutineMainClass from '@/entities/routine-main/routine-main-details.component';
import RoutineMainService from '@/entities/routine-main/routine-main.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RoutineMain Management Detail Component', () => {
    let wrapper: Wrapper<RoutineMainClass>;
    let comp: RoutineMainClass;
    let routineMainServiceStub: SinonStubbedInstance<RoutineMainService>;

    beforeEach(() => {
      routineMainServiceStub = sinon.createStubInstance<RoutineMainService>(RoutineMainService);

      wrapper = shallowMount<RoutineMainClass>(RoutineMainDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { routineMainService: () => routineMainServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRoutineMain = { id: 'ABC' };
        routineMainServiceStub.find.resolves(foundRoutineMain);

        // WHEN
        comp.retrieveRoutineMain('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.routineMain).toBe(foundRoutineMain);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRoutineMain = { id: 'ABC' };
        routineMainServiceStub.find.resolves(foundRoutineMain);

        // WHEN
        comp.beforeRouteEnter({ params: { routineMainId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.routineMain).toBe(foundRoutineMain);
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
