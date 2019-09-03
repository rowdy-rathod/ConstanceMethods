package com.rowdy.common_methods.utils;


import com.rowdy.common_methods.R;

public final class IconFactory {
    private IconFactory() {}

    public static int getIconForAlertType(Enums.AlertType alertType) {
        switch (alertType) {
            case ALERT:
                return R.drawable.ic_dialog_alert;
            case INFO:
                return R.drawable.ic_dialog_info;
            case NETWORK_ERROR:
                return R.drawable.ic_dialog_network_error;
            default:
                return R.drawable.ic_dialog_error;
        }
    }

    public static int getTabIcon(Enums.Tab tab) {
        switch (tab) {
//            case ABOUT_US:
//                return R.drawable.ict_about_us;
//            case CONTACT_US:
//                return R.drawable.ict_contact_us;
//            case COURSE:
//                return R.drawable.ict_courses;
//            case FRANCHISE:
//                return R.drawable.ict_franchise;
//            case HOME:
//                return R.drawable.ict_home;
//            case KNOWLEDGE:
//                return R.drawable.ict_knowledge;
//            case PLACEMENT_COMPNIES:
//                return R.drawable.ict_placement_compnies;
//            case PLACEMENTS:
//                return R.drawable.ict_placement;
//            case SYLLABUS:
//                return R.drawable.ict_syllabas;
//            case CANDIDATE_WORK:
//                return R.drawable.ict_candidate_work;
//            case WHY_CHOOSE_US:
//                return R.drawable.ict_why_choose_us;
//            case GALLERY:
//                return R.drawable.ict_gallery;
//            case REVIEW_AND_TESTIMONAIL:
//                return R.drawable.ict_review_and_testimonail;
//
//            default:
//                return R.drawable.ict_about_us;
        }
        return 0;
    }

}
