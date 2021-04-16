package org.openelisglobal.common.provider.validation;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;
import org.openelisglobal.common.util.ConfigurationProperties;
import org.openelisglobal.common.util.ConfigurationProperties.Property;
import org.openelisglobal.common.util.DateUtil;
import org.openelisglobal.internationalization.MessageUtil;

public class AltYearAccessionValidator extends BaseSiteYearAccessionValidator implements IAccessionNumberGenerator {

    private String startingAt;
    private Set<String> localReservedNumbers = new HashSet<>();

    @Override
    public String getInvalidMessage(ValidationResults results) {
        String suggestedAccessionNumber = getNextAvailableAccessionNumber(null, false);

        return MessageUtil.getMessage("sample.entry.invalid.accession.number.suggestion") + " "
                + suggestedAccessionNumber;

    }

    @Override
    public int getInvarientLength() {
        return getSiteEndIndex();
    }

    @Override
    public String getPrefix() {
        return ConfigurationProperties.getInstance().getPropertyValue(Property.ALT_ACCESSION_PREFIX);
    }

    @Override
    protected int getIncrementStartIndex() {
        return getSiteEndIndex() + 2;
    }

    @Override
    protected int getYearEndIndex() {
        return getSiteEndIndex() + 2;
    }

    @Override
    protected int getYearStartIndex() {
        return getSiteEndIndex();
    }

    @Override
    protected int getSiteEndIndex() {
        return getPrefix().length();
    }

    @Override
    public int getMaxAccessionLength() {
        return getSiteEndIndex() + 15;
    }

    @Override
    public int getMinAccessionLength() {
        return getMaxAccessionLength();
    }

    @Override
    public int getChangeableLength() {
        return getMaxAccessionLength() - getInvarientLength();
    }

    @Override
    public String getOverrideStartingAt() {
        return startingAt;
    }

    public void setOverrideStartingAt(String startingAt) {
        this.startingAt = startingAt;
    }

    @Override
    public String incrementAccessionNumber() throws IllegalArgumentException {
        if (GenericValidator.isBlankOrNull(startingAt)) {

            long nextNum = accessionDAO.getNextNumberForAltYearFormatIncrement();
            String year = DateUtil.getTwoDigitYear();
            String incrementAsString;

            incrementAsString = String.format("%013d", nextNum);

            return getPrefix() + year + incrementAsString;
        } else {
            String nextAccessionNumber = startingAt;
            while (localReservedNumbers.contains(nextAccessionNumber)) {
                nextAccessionNumber = incrementAccessionNumber(startingAt);
            }
            return nextAccessionNumber;
        }
    }

    public String incrementAccessionNumber(String currentHighAccessionNumber) throws IllegalArgumentException {
        Long increment = Long.parseLong(currentHighAccessionNumber.substring(INCREMENT_START));
        String incrementAsString;

        if (increment < UPPER_INC_RANGE) {
            increment++;
            incrementAsString = String.format("%013d", increment);
        } else {
            throw new IllegalArgumentException("AccessionNumber has no next value");
        }

        return currentHighAccessionNumber.substring(SITE_START, YEAR_END) + incrementAsString;
    }

}
