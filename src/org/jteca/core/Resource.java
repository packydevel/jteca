package org.jteca.core;
/*******************************************************************************
 * Copyright (C) Copyright (C) 2010 JTeca Team - support@gmail.com
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 ******************************************************************************/
/**
 * @author packyuser
 *
 */
abstract class Resource {
    private String name, playbill, notes;
    private ResourceType type;
    /** Restituisce il nome
     *
     * @return the name
     */
    public String getName() {
            return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**Restituisce il tipo
     *
     * @return the type
     */
    public ResourceType getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(ResourceType type) {
        this.type = type;
    }
    /**Restituisce le note
     *
     * @return
     */
    public String getNotes() {
        return notes;
    }
    /**
     *
     * @param notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    /**restituisce la locandina
     *
     * @return
     */
    public String getPlaybill() {
        return playbill;
    }

    public void setPlaybill(String playbill) {
        this.playbill = playbill;
    }
}