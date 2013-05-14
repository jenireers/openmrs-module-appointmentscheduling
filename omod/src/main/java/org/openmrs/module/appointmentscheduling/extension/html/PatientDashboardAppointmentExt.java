package org.openmrs.module.appointmentscheduling.extension.html;

import java.util.Map;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.Extension;
import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.appointmentscheduling.AppointmentUtils;
import org.openmrs.module.appointmentscheduling.Appointment.AppointmentStatus;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;

public class PatientDashboardAppointmentExt extends Extension {
	
	private String patientId = "";
	
	@Override
	public void initialize(final Map<String, String> parameters) {
		patientId = parameters.get("patientId");
	}
	
	@Override
	public MEDIA_TYPE getMediaType() {
		return MEDIA_TYPE.html;
	}
	
	@Override
	public String getOverrideContent(String bodyContent) {
		Patient patient = Context.getPatientService().getPatient(Integer.parseInt(patientId));
		Appointment appointment = Context.getService(AppointmentService.class).getLastAppointment(patient);
		
		if (!Context.hasPrivilege(AppointmentUtils.PRIV_UPDATE_APPOINTMENT_STATES))
			return "";
		
		//Check if latest appointment is In Consultation
		if (appointment != null && appointment.getStatus() == AppointmentStatus.INCONSULTATION) {
			String value = Context.getMessageSourceService().getMessage(
			    "appointmentscheduling.Appointment.list.button.endConsultation");
			String action = "endConsult";
			String style = "<style>"
			        + ".saveButton {background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #A3A3A3), color-stop(1, #757575) );	background: -moz-linear-gradient(center top, #A3A3A3 0%, #757575 100%   );	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#A3A3A3',	endColorstr='#757575' ); background-color: #a2dec8;	border: 1px solid #CCCCCC;	display: inline-block;	color: #ffffff;	padding: 8px 35px;	text-decoration: none;	text-shadow: 1px 1px 0px #666666;	font-weight: bold;	font-size:16px;	border-radius:6px;}"
			        + ".saveButton:hover:enabled {background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #757575), color-stop(1, #A3A3A3) );	background: -moz-linear-gradient(center top, #757575 0%, #A3A3A3 100%   );	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#757575',		endColorstr='#A3A3A3' );	background-color: #a2dec8;	cursor:pointer;}"
			        + "</style>";
			return style
			        + "<input type=\"button\" class=\"saveButton\" value=\""
			        + value
			        + "\" onclick=\"window.location.href='module/appointmentscheduling/patientDashboardAppointmentExt.form?patientId="
			        + patientId + "&action=" + action + "'\" />";
		}
		//Check if latest appointment is Waiting
		else if (appointment != null
		        && (appointment.getStatus() == AppointmentStatus.WAITING || appointment.getStatus() == AppointmentStatus.WALKIN)) {
			String value = Context.getMessageSourceService().getMessage(
			    "appointmentscheduling.Appointment.list.button.startConsultation");
			String action = "startConsult";
			String style = "<style>"
			        + ".saveButton {background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #A3A3A3), color-stop(1, #757575) );	background: -moz-linear-gradient(center top, #A3A3A3 0%, #757575 100%   );	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#A3A3A3',	endColorstr='#757575' ); background-color: #a2dec8;	border: 1px solid #CCCCCC;	display: inline-block;	color: #ffffff;	padding: 8px 35px;	text-decoration: none;	text-shadow: 1px 1px 0px #666666;	font-weight: bold;	font-size:16px;	border-radius:6px;}"
			        + ".saveButton:hover:enabled {background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #757575), color-stop(1, #A3A3A3) );	background: -moz-linear-gradient(center top, #757575 0%, #A3A3A3 100%   );	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#757575',		endColorstr='#A3A3A3' );	background-color: #a2dec8;	cursor:pointer;}"
			        + "</style>";
			return style
			        + "<input type=\"button\" class=\"saveButton\" value=\""
			        + value
			        + "\" onclick=\"window.location.href='module/appointmentscheduling/patientDashboardAppointmentExt.form?patientId="
			        + patientId + "&action=" + action + "'\" />";
		}
		
		return "";
		
	}
}