/*
* This file is part of Wakanda software, licensed by 4D under
*  (i) the GNU General Public License version 3 (GNU GPL v3), or
*  (ii) the Affero General Public License version 3 (AGPL v3) or
*  (iii) a commercial license.
* This file remains the exclusive property of 4D and/or its licensors
* and is protected by national and international legislations.
* In any event, Licensee's compliance with the terms and conditions
* of the applicable license constitutes a prerequisite to any use of this file.
* Except as otherwise expressly stated in the applicable license,
* such license does not include any other license or rights on this file,
* 4D's and/or its licensors' trademarks and/or other proprietary rights.
* Consequently, no title, copyright or other proprietary rights
* other than those specified in the applicable license is granted.
*/
/**
 * Attribute of Tag Descriptor
 * @namespace WAF.tags.descriptor
 * @class AttributeColumn
 * @params {Object} config parameters
 */
WAF.tags.descriptor.AttributeColumn = function(config) {
    config = config || {};

    config.name             = config.name         || '';
    config.defaultValue     = config.defaultValue || '';

    // properties inherited from config
    this._name              = config.name;
    this._defaultValue      = config.defaultValue;

    // property
    this._value             = '';
    this._oldValue          = '';
    
    // defaultValue
    this._value = this._defaultValue;
};


// GETTER


/**
 * Get the value of the name of the attribute
 * @namespace WAF.tags.descriptor.AttributeColumn
 * @method getName
 * @return {String} the value of the attribute
 */
WAF.tags.descriptor.AttributeColumn.prototype.getName = function () {
    return this._name;
};

/**
 * Get the value of the default value of the attribute
 * @namespace WAF.tags.descriptor.AttributeColumn
 * @method getDefaultValue
 * @return {String} the value of the attribute
 */
WAF.tags.descriptor.AttributeColumn.prototype.getDefaultValue = function () {
    return this._defaultValue;
};

/**
 * Get the value of the attribute
 * @namespace WAF.tags.descriptor.AttributeColumn
 * @method getValue
 * @return {String} value of the attribute
 */
WAF.tags.descriptor.AttributeColumn.prototype.getValue = function () {    
    return this._value;
};

/**
 * Get the old value of the attribute
 * @namespace WAF.tags.descriptor.AttributeColumn
 * @method getOldValue
 * @return {String} value of the attribute
 */
WAF.tags.descriptor.AttributeColumn.prototype.getOldValue = function () {    
    return this._oldValue;
};


// SETTER


/**
 * Set the value of the attribute
 * @namespace WAF.tags.descriptor.AttributeColumn
 * @method setValue
 * @param {String} value new value of the attribute
 */
WAF.tags.descriptor.AttributeColumn.prototype.setValue = function (value) {
    this._oldValue = this._value;
    this._value = value;
};