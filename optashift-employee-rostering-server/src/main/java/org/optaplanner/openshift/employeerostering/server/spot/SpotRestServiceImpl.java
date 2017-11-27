/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.openshift.employeerostering.server.spot;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.optaplanner.openshift.employeerostering.server.common.AbstractRestServiceImpl;
import org.optaplanner.openshift.employeerostering.shared.spot.Spot;
import org.optaplanner.openshift.employeerostering.shared.spot.SpotRestService;

public class SpotRestServiceImpl extends AbstractRestServiceImpl implements SpotRestService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Spot> getSpotList(Integer tenantId) {
        return entityManager.createNamedQuery("Spot.findAll", Spot.class)
                .setParameter("tenantId", tenantId)
                .getResultList();
    }

    @Override
    @Transactional
    public Spot getSpot(Integer tenantId, Long id) {
        Spot spot = entityManager.find(Spot.class, id);
        validateTenantIdParameter(tenantId, spot);
        return spot;
    }

    @Override
    @Transactional
    public Long addSpot(Integer tenantId, Spot spot) {
        validateTenantIdParameter(tenantId, spot);
        entityManager.persist(spot);
        return spot.getId();
    }

    @Override
    @Transactional
    public Boolean updateSpot(Integer tenantId, Long id, Spot newValue) {
        validateTenantIdParameter(tenantId, newValue);
        update(entityManager, newValue, id);
        return true;
    }

    @Override
    @Transactional
    public Boolean removeSpot(Integer tenantId, Long id) {
        Spot spot = entityManager.find(Spot.class, id);
        if (spot == null) {
            return false;
        }
        validateTenantIdParameter(tenantId, spot);
        entityManager.remove(spot);
        return true;
    }

}
