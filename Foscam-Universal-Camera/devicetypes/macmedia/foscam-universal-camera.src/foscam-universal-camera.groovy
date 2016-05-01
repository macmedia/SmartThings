/**
 *  Foscam Universal Device
 *
 *  Copyright 2016 macmedia
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
    definition (name: "Foscam Universal Camera Device", namespace: "macmedia", author: "Mike Elser") {
        capability "Polling"
        capability "Configuration"
        capability "Image Capture"
        capability "Motion Sensor"
        capability "Refresh"
        capability "Switch"
        capability "Video Camera"
        capability "Video Capture"

        attribute "alarmStatus", "string"
        attribute "ledStatus",   "string"
        attribute "hubactionMode", "string"
        attribute "streamType", "string"

        command "toggleLED"
        command "rebootDevice"

        command "ledOn"
        command "ledOff"
        command "ledAuto"

        command "videoStart"
        command "videoStop"
        command "videoSetResHD"
        command "videoSetResSD"
        command "toggleStreamType"

    }
    simulator{}
    

    tiles(scale: 2) {
        //carouselTile("cameraDetails", "device.image", width: 3, height: 2) { }
        multiAttributeTile(name: "videoPlayer", type: "videoPlayer", width: 6, height: 4) {
            tileAttribute("device.switch", key: "CAMERA_STATUS") {
                attributeState("on", label: "Active", action: "switch.off", icon: "http://smartthings.belgarion.s3.amazonaws.com/images/IPM-721S.png", backgroundColor: "#79b821", defaultState: true)
                attributeState("off", label: "Inactive", action: "switch.on", icon: "http://smartthings.belgarion.s3.amazonaws.com/images/IPM-721S.png", backgroundColor: "#ffffff")
                attributeState("restarting", label: "Connecting", icon: "http://smartthings.belgarion.s3.amazonaws.com/images/IPM-721S.png", backgroundColor: "#53a7c0")
                attributeState("unavailable", label: "Unavailable", action: "refresh.refresh", icon: "http://smartthings.belgarion.s3.amazonaws.com/images/IPM-721S.png", backgroundColor: "#F22000")
            }
            tileAttribute("device.errorMessage", key: "CAMERA_ERROR_MESSAGE") {
                attributeState("errorMessage", label: "", value: "", defaultState: true)
            }
            tileAttribute("device.camera", key: "PRIMARY_CONTROL") {
                attributeState("on", label: "Active", icon: "http://smartthings.belgarion.s3.amazonaws.com/images/IPM-721S.png", backgroundColor: "#79b821", defaultState: true)
                attributeState("off", label: "Inactive", icon: "http://smartthings.belgarion.s3.amazonaws.com/images/IPM-721S.png", backgroundColor: "#ffffff")
                attributeState("restarting", label: "Connecting", icon: "http://smartthings.belgarion.s3.amazonaws.com/images/IPM-721S.png", backgroundColor: "#53a7c0")
                attributeState("unavailable", label: "Unavailable", icon: "http://smartthings.belgarion.s3.amazonaws.com/images/IPM-721S.png", backgroundColor: "#F22000")
            }
            tileAttribute("device.startLive", key: "START_LIVE") {
                attributeState("live", action: "videoStart", defaultState: true)
            }
            tileAttribute("device.stream", key: "STREAM_URL") {
                attributeState("activeURL", defaultState: true)
            }
            tileAttribute("device.profile", key: "STREAM_QUALITY") {
                attributeState("hd", label: "HD", action: "videoSetResHD", defaultState: true)
                attributeState("sd", label: "SD", action: "videoSetResSD")
            }
            tileAttribute("device.betaLogo", key: "BETA_LOGO") {
                attributeState("betaLogo", label: "", value: "", defaultState: true)
            }
        }

        standardTile("take", "device.image", width: 2, height: 2, canChangeIcon: false, canChangeBackground: false) {
            state "take", label: "Take", action: "Image Capture.take", icon: "st.camera.camera", backgroundColor: "#FFFFFF", nextState:"taking"
            state "taking", label:'Taking', action: "", icon: "st.camera.take-photo", backgroundColor: "#53a7c0"
            state "image", label: "Take", action: "Image Capture.take", icon: "st.camera.camera", backgroundColor: "#FFFFFF", nextState:"taking"
        }

        standardTile("alarmStatus", "device.alarmStatus", width: 2, height: 2, canChangeIcon: false, canChangeBackground: false) {
          state "off", label: '${name}', action: "on", icon: "st.camera.dropcam", backgroundColor: "#FFFFFF"
          state "on", label: '${name}', action: "off", icon: "st.camera.dropcam",  backgroundColor: "#53A7C0"
        }

        standardTile("ledStatus", "device.ledStatus", width: 2, height: 2, canChangeIcon: false, canChangeBackground: false) {
          state "auto", label: "auto", action: "toggleLED", icon: "st.Lighting.light13", backgroundColor: "#53A7C0"
          state "off", label: "off", action: "toggleLED", icon: "st.Lighting.light13", backgroundColor: "#FFFFFF"
          state "on", label: "on", action: "toggleLED", icon: "st.Lighting.light11", backgroundColor: "#FFFF00"
          state "manual", label: "manual", action: "toggleLED", icon: "st.Lighting.light13", backgroundColor: "#FFFF00"
        }

        standardTile("ledAuto", "device.ledStatus", width: 2, height: 2, canChangeIcon: false, canChangeBackground: false) {
          state "auto", label: "auto", action: "ledAuto", icon: "st.Lighting.light11", backgroundColor: "#53A7C0"
          state "off", label: "auto", action: "ledAuto", icon: "st.Lighting.light13", backgroundColor: "#FFFFFF"
          state "on", label: "auto", action: "ledAuto", icon: "st.Lighting.light13", backgroundColor: "#FFFFFF"
          state "manual", label: "auto", action: "ledAuto", icon: "st.Lighting.light13", backgroundColor: "#FFFFFF"
        }

        standardTile("ledOn", "device.ledStatus", width: 2, height: 2, canChangeIcon: false, canChangeBackground: false) {
          state "auto", label: "on", action: "ledOn", icon: "st.Lighting.light11", backgroundColor: "#FFFFFF"
          state "off", label: "on", action: "ledOn", icon: "st.Lighting.light11", backgroundColor: "#FFFFFF"
          state "on", label: "on", action: "ledOn", icon: "st.Lighting.light11", backgroundColor: "#FFFF00"
          state "manual", label: "on", action: "ledOn", icon: "st.Lighting.light11", backgroundColor: "#00FF00"
        }

        standardTile("ledOff", "device.ledStatus", width: 2, height: 2, canChangeIcon: false, canChangeBackground: false) {
          state "auto", label: "off", action: "ledOff", icon: "st.Lighting.light13", backgroundColor: "#FFFFFF"
          state "off", label: "off", action: "ledOff", icon: "st.Lighting.light13", backgroundColor: "#53A7C0"
          state "on", label: "off", action: "ledOff", icon: "st.Lighting.light13", backgroundColor: "#FFFFFF"
          state "manual", label: "off", action: "ledOff", icon: "st.Lighting.light13", backgroundColor: "#00FF00"
        }

        standardTile("refresh", "device.refresh", width: 2, height: 2, decoration: "flat") {
          state "refresh", action:"polling.poll", icon:"st.secondary.refresh"
        }

        standardTile("reboot", "device.reboot", width: 2, height: 2, canChangeIcon: false, canChangeBackground: false, decoration: "flat"){
            state "reboot", label: '${name}', action: "rebootDevice", icon: "st.samsung.da.RC_ic_power"
        }


        htmlTile(name:"devInfoHtml", action: "getInfoHtml", width: 6, height: 5)

        main (["alarmStatus"])
        details(["videoPlayer", "take", "reboot", "alarmStatus", "ledAuto", "ledOn", "ledOff", "refresh","devInfoHtml"])
    }
}



mappings {
   path("/getInHomeURL")    {action: [GET: "getInHomeURL"]}
   path("/getOutHomeURL")   {action: [GET: "getOutHomeURL"]}
   path("/getInfoHtml")     {action: [GET: "getInfoHtml"]}
}

//CONFIGURE
def configure() {
    doDebug("configure -> Executing")

    state.testingState = "initalized"

    log.debug("State: $state.testingState")
    sendEvent(name: "switch", value: "on")
}

def getInHomeURL() {
    //[InHomeURL: parent.state.CameraStreamIP]
    [InHomeURL: parent.getStreamURI()]
}

def getOutHomeURL() {
    //[OutHomeURL: parent.state.CameraStreamIP]
    [OutHomeURL: parent.getStreamURI()]
}

def installed() {
    log.debug("Device -> Configure")
    configure()
}

def updated() {
    log.debug("Device -> Update")
    configure()
}

//def toggleStreamType() {
//    if (device.currentValue('streamType') != "MJPEG") {
//        log.info "toggleStreamType -> Image Streaming Mode Now 'MJPEG'"
//        sendEvent(name: "streamType", value: "MJPEG", isStateChange: true, displayed: false)
//    }
//    else {
//        log.info "toggleStreamType -> Image Streaming Mode Now 'RTSP'"
//        sendEvent(name: "streamType", value: "RTSP", isStateChange: true, displayed: false)
//    }
//}

//LIVE VIDEO

def videoSetProfile(profile) {
    log.info "videoSetProfile -> ${profile}"
    sendEvent(name: "profile", value: profile, displayed: false)
}

def videoSetResHD() {
    log.info "videoSetResHD -> Set video to HD stream"
    sendEvent(name: "profile", value: "hd", displayed: false)
}

def videoSetResSD() {
    log.info "videoSetResSD -> Set video to SD stream"
    sendEvent(name: "profile", value: "sd", displayed: false)
}

def videoStart(){
    log.info "videoStart -> Turning Video Streaming ON"
    //def userPassAscii = "${username}:${password}"
    //def usePort = 554
    //def useProtocol = parent.cameraStreamProtocol
    def uri = parent.getStreamURI() //useProtocol + userPassAscii + "@${ip}:${usePort}" + "/videoMain"
    def ip  = parent.cameraStreamIP

    log.info("CamURI: $uri")
    log.info("CamIP: $ip")

    //doDebug("videoStart -> Streaming ${device.currentValue('streamType')} video; apiCommand = ${uri}, IP = ${ip}, Port = ${port}")

    //parent.state.CameraStreamPath
    state.CameraInStreamPath = uri
    state.CameraOutStreamPath = ""
    if (isIpAddress(ip) != true) {
        state.CameraOutStreamPath = uri
    }
    else if (!ipIsLocal(ip) != true) {
        state.CameraOutStreamPath = uri
    }

    // Only Public IP's work for 'OutHomeURL'
    def dataLiveVideo = [
            //OutHomeURL  : parent.state.CameraStreamPath,
            //InHomeURL   : parent.state.CameraStreamPath,
            OutHomeURL  : state.CameraOutStreamPath,
            InHomeURL   : state.CameraInStreamPath,
            ThumbnailURL: "http://cdn.device-icons.smartthings.com/camera/dlink-indoor@2x.png",
            cookie      : [key: "key", value: "value"]
    ]

    def event = [
            name           : "stream",
            value          : groovy.json.JsonOutput.toJson(dataLiveVideo).toString(),
            data           : groovy.json.JsonOutput.toJson(dataLiveVideo),
            descriptionText: "Starting the livestream",
            eventType      : "VIDEO",
            displayed      : false,
            isStateChange  : true
    ]
    sendEvent(event)
}

def videoStop() {
    log.info "videoStop -> Turning Video Streaming OFF"
}

//TAKE PICTURE
def take() {
    doDebug("Taking Photo")
    sendEvent(name: "hubactionMode", value: "s3");
    if(hdcamera == true) {
        hubGet("cmd=snapPicture2")
    }
    else {
        hubGet("/snapshot.cgi?")
    }
}
//END TAKE PICTURE

//REBOOT CAM
def rebootDevice(){
    if(hdcamera == true) {
        log.info "Rebooting Cam"
        sendEvent(name: "reboot", value: "Rebooting");
        hubGet("cmd=rebootSystem")
    }
    else {
        log.info "Unable to Reboot Cam"
    }
}

//ALARM ACTIONS

def on() {
    doDebug "Enabling Alarm"
    sendEvent(name: "alarmStatus", value: "on");

    if(parent.cameraStreamHD == true) {
       hubGet("cmd=setMotionDetectConfig&isEnable=1&linkage=136&snapInterval=2&sensitivity=1&triggerInterval=0&schedule0=281474976710655&schedule1=281474976710655&schedule2=281474976710655&schedule3=281474976710655&schedule4=281474976710655&schedule5=281474976710655&schedule6=281474976710655&area0=1024&area1=1024&area2=1024&area3=1024&area4=1024&area5=1024&area6=1024&area7=1024&area7=1024&area8=1024&area9=1024")
    }
    else {
        hubGet("/set_alarm.cgi?motion_armed=1&")
    }
}

def off() {
    doDebug "Disabling Alarm"
    sendEvent(name: "alarmStatus", value: "off");

    if(parent.cameraStreamHD == true) {
        hubGet("cmd=setMotionDetectConfig&isEnable=0")
    }
    else {
        hubGet("/set_alarm.cgi?motion_armed=0&")
    }
}
//END ALARM ACTIONS

//LED ACTIONS
//Toggle LED's
def toggleLED() {
  if(device.currentValue("ledStatus") == "auto") {
    ledOn()
  }

  else if(device.currentValue("ledStatus") == "on") {
    ledOff()
  }

  else {
    ledAuto()
  }
}

def ledOn() {
    doDebug("LED changed to: on")
    sendEvent(name: "ledStatus", value: "on");
    if(parent.cameraStreamHD == true) {
        delayBetween([hubGet("cmd=setInfraLedConfig&mode=1"), hubGet("cmd=openInfraLed")])
    }
    else {
        hubGet("/decoder_control.cgi?command=95&")
    }
}

def ledOff() {
    doDebug("LED changed to: off")
    sendEvent(name: "ledStatus", value: "off");
    if(parent.cameraStreamHD == true) {
        delayBetween([hubGet("cmd=setInfraLedConfig&mode=1"), hubGet("cmd=closeInfraLed")])
    }
    else {
        hubGet("/decoder_control.cgi?command=94&")
    }
}

def ledAuto() {
    doDebug("LED changed to: auto")
    sendEvent(name: "ledStatus", value: "auto");
    if(parent.cameraStreamHD == true) {
        hubGet("cmd=setInfraLedConfig&mode=0")
    }
    else {
        hubGet("/decoder_control.cgi?command=95&")
    }
}
//END LED ACTIONS


def poll() {
    log.info("Polled for Status")

    sendEvent(name: "hubactionMode", value: "local");

    //Poll Motion Alarm Status and IR LED Mode
    if(parent.cameraStreamHD == true) {
        delayBetween([hubGet("cmd=getMotionDetectConfig"), hubGet("cmd=getInfraLedConfig")])
    }
    else {
        hubGet("/get_params.cgi?")
    }
}

private getLogin() {
    log.debug("Device -> getLogin")
    if(parent.cameraStreamHD == true) {
        return "usr=${parent.cameraStreamUser}&pwd=${parent.cameraStreamPwd}&"
    }
    else {
        return "user=${parent.cameraStreamUser}&pwd=${parent.cameraStreamPwd}"
    }
}

private hubGet(def apiCommand) {
    //Setting Network Device Id
    log.debug("Started hubGet")

    def iphex   = convertIPtoHex(parent.cameraStreamIP)
    def porthex = convertPortToHex(parent.cameraStreamPort)
    def uri     = ""
    def address = getHostAddress()

    log.debug("convertIPtoHex: ${iphex}")
    log.debug("convertIPtoHex: ${porthex}")

    device.deviceNetworkId = "$iphex:$porthex"
    doDebug("Device Network Id set to ${iphex}:${porthex}")

    doDebug("Executing hubaction on " + address)


    if(parent.cameraStreamHD == true) {
        uri = "/cgi-bin/CGIProxy.fcgi?" + getLogin() + apiCommand
    }
    else {
        uri = apiCommand + getLogin()
    }
    doDebug("Command URL: ${address}${uri}")

    def hubAction = new physicalgraph.device.HubAction(
        method: "GET",
        path: uri,
        headers: [HOST:address]
    )
    if(device.currentValue("hubactionMode") == "s3") {
        hubAction.options = [outputMsgToS3:true]
        sendEvent(name: "hubactionMode", value: "local");
    }
    return hubAction

}

//Parse events into attributes

def parse(String description) {
    log.debug(">>> Parsing '${description}'")

    //Need to check on this. The child app calls this and causes array index errors when called from updated
    if(description == "updated") return

    def map = [:]
    def retResult = []
    def descMap = parseDescriptionAsMap(description)

    //Image
    if (descMap["bucket"] && descMap["key"]) {
        putImageInS3(descMap)
    }

    //Status Polling
    else if (descMap["headers"] && descMap["body"]) {
        def body = new String(descMap["body"].decodeBase64())
        if(parent.cameraStreamHD == true) {
            def langs = new XmlSlurper().parseText(body)

            def motionAlarm = "$langs.isEnable"
            def ledMode = "$langs.mode"

            //Get Motion Alarm Status
            if(motionAlarm == "0") {
                log.info("Polled: Alarm Off")
                sendEvent(name: "alarmStatus", value: "off")
            }
            else if(motionAlarm == "1") {
                log.info("Polled: Alarm On")
                sendEvent(name: "alarmStatus", value: "on")
            }

            //Get IR LED Mode
            if(ledMode == "0") {
                log.info("Polled: LED Mode Auto")
                sendEvent(name: "ledStatus", value: "auto")
            }
            else if(ledMode == "1") {
                log.info("Polled: LED Mode Manual")
                sendEvent(name: "ledStatus", value: "manual")
            }
        }
        else {
            if(body.find("alarm_motion_armed=0")) {
                log.info("Polled: Alarm Off")
                sendEvent(name: "alarmStatus", value: "off")
            }
            else if(body.find("alarm_motion_armed=1")) {
                log.info("Polled: Alarm On")
                sendEvent(name: "alarmStatus", value: "on")
            }
            //The API does not provide a way to poll for LED status on 8xxx series at the moment
        }
    }
}

def parseDescriptionAsMap(description) {
    log.debug("Device -> Map")
    description.split(",").inject([:]) { map, param ->
        def nameAndValue = param.split(":")

        try{
            map += [(nameAndValue[0].trim()):nameAndValue[1].trim()]
        } catch (e){
            log.error("Error thrown in parseDescriptionAsMap : $e")
        }

    }
}

def putImageInS3(map) {
    log.debug("Device -> S3_Image")
    def s3ObjectContent

    try {
        def imageBytes = getS3Object(map.bucket, map.key + ".jpg")

        if(imageBytes)
        {
            s3ObjectContent = imageBytes.getObjectContent()
            def bytes = new ByteArrayInputStream(s3ObjectContent.bytes)
            storeImage(getPictureName(), bytes)
        }
    }
    catch(Exception e) {
        log.error e
    }
    finally {
        //Explicitly close the stream
        if (s3ObjectContent) { s3ObjectContent.close() }
    }
}

private getPictureName() {
    log.debug("Device -> getPicName")
    def pictureUuid = java.util.UUID.randomUUID().toString().replaceAll('-', '')
    "image" + "_$pictureUuid" + ".jpg"
}

private getHostAddress() {
    log.debug("Device -> getHostAddr")
    return "${parent.cameraStreamIP}:${parent.cameraStreamPort}"
}

private String convertIPtoHex(ipAddress) {
    log.debug("Device -> convert ip to hex")
    String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
    return hex.toUpperCase()

}

private String convertPortToHex(port) {
    log.debug("Device -> conert port to hex")
    String hexport = port.toString().format( '%04x', port.toInteger() )
    return hexport.toUpperCase()
}

private Boolean isIpAddress(String str) {
    log.debug("Device -> isIP")
    // See: http://stackoverflow.com/questions/18157217/how-can-i-check-if-a-string-is-an-ip-in-groovy
    try {
        String[] parts = str.split("\\.")
        if (parts.length != 4) {
            return false
        }
        for (int i = 0; i < 4; ++i) {
            int p = Integer.parseInt(parts[i])
            if (p > 255 || p < 0) {
                return false
            }
        }
        return true
    }
    catch ( Exception e ) {
        log.error "Unable to determine if IP Address is valid ($str), Error: $e"
        return false
    }
}

private Boolean ipIsLocal(ipAddress) {
    log.debug("Device -> isLocalIP")
    List ipPrivateRanges = ["00000000",          // 'LOCAL IP',  # 0/8
                            "00001010",          // 'LOCAL IP',  # 10/8
                            "01111111",          // 'LOCAL IP',  # 127.0/8
                            "1010100111111110",  // 'LOCAL IP',  # 169.254/16
                            "101011000001",      // 'LOCAL IP',  # 172.16/12
                            "1100000010101000"]  // 'LOCAL IP',  # 192.168/16
    def size = 17
    Boolean localAns = false
    try {
        def ipBinary = convertIPtoBinary(ipAddress)
        ipBinary = ipBinary.take(size)

        while (size-- > 7) {
            if (ipPrivateRanges.contains(ipBinary)) {
                doDebug("ipIsLocal -> Found = $ipBinary")
                localAns = true
                break
            }
            ipBinary = ipBinary.take(size)
        }
        doDebug("ipIsLocal -> Host IP '$ipAddress' is ${localAns ? 'Local' : 'Public' }")
        return localAns
    }
    catch (Exception e) {
        log.error "Exception: $e"
        return false
    }
    return localAns
}


private String convertIPtoBinary(ipAddress) {
    log.debug("Device -> ipToBinary")
    try {
        def bin = ""
        def oct = ""
        ipAddress.tokenize( '.' ).collect {
            oct = String.format( '%8s', Integer.toString(it.toInteger(), 2) ).replace(' ', '0')
            bin = bin + oct
        }
        doDebug("convertIPtoBinary -> IP address passed in is $ipAddress and the converted binary is $bin")
        return bin
    }
    catch ( Exception e ) {
        log.error "IP Address is invalid ($ipAddress), Error: $e"
        return null // Nothing to return
    }
}

//***********************************  Debugging  **********************************

private doDebug(Object... dbgStr) {
    if (camDebug) {
        log.debug dbgStr
    }
}


//*********************************** HTML TILE **********************************
def getInfoHtml() {

    //Create defaults
    def _debug_mode = "N/A"
    def _ip_address = "N/A"
    def _ip_stream  = "N/A"
    def _ip_port    = "N/A"
    def _camera_hd  = "N/A"
    def _ip_stream_port = "N/A"
    def _stream_uri = "N/A"

    //Setup variables
    if (parent?.cameraStreamDebug)      _debug_mode = "TRUE"
    if (parent?.cameraStreamIP)         _ip_address = parent.cameraStreamIP.toString()
    if (parent?.cameraStreamPort)       _ip_port    = parent.cameraStreamPort.toString()
    if (parent?.cameraStreamProtocol)   _ip_stream  = parent.cameraStreamProtocol.toString().toUpperCase()
    if (parent?.cameraStreamRTSPPort)   _ip_stream_port = parent.cameraStreamRTSPPort.toString()
    if (parent?.cameraStreamHD)         _camera_hd  = parent.cameraStreamHD.toString().toUpperCase()

    //Build Stream URI to display
    _stream_uri =   parent?.cameraStreamProtocol +
                    parent?.cameraStreamUser + ":*****@" +
                    parent?.cameraStreamIP + ":" +
                    parent?.cameraStreamRTSPPort +"/videoMain"


    //Create the html to show in html tile
    renderHTML {
        head {
        """
        <style type="text/css">
             .flat-table {
              width: 100%;
              font-family: 'San Francisco', 'Roboto', 'Arial';
              border: none;
              border-radius: 3px;
              -webkit-border-radius: 3px;
              -moz-border-radius: 3px;
            }

            .flat-table th,
            .flat-table td {
              box-shadow: inset 0 0px rgba(0, 0, 0, 0.25), inset 0 0px rgba(0, 0, 0, 0.25);
              padding: 4px;
            }

            .flat-table th {
              -webkit-font-smoothing: antialiased;
              color: #f5f5f5;
              text-shadow: 0 0 1px rgba(0, 0, 0, 0.1);
              -webkit-border-radius: 2px;
              -moz-border-radius: 2px;
              background: #00a1db;
            }

            .flat-table td {
              color: grey;
              text-shadow: 0 0 1px rgba(255, 255, 255, 0.1);
              text-align: center;
            }

            .flat-table tr {
              -webkit-transition: background 0.3s, box-shadow 0.3s;
              -moz-transition: background 0.3s, box-shadow 0.3s;
              transition: background 0.3s, box-shadow 0.3s;
            }
            .h100 {
                  width: 99.99%;
                  font-weight: bold;
                  font-size: 3.2vmax;
            }
            .h60 {
                  width: 59.99%;
                  font-weight: bold;
                  font-size: 3.2vmax;
            }
            .h40 {
                  width: 39.99%;
                  font-weight: bold;
                  font-size: 3.2vmax;
            }
            .h20 {
                  width: 19.99%;
                  font-weight: bold;
                  font-size: 3.4vmax;
            }
            .r100 {
                width: 99.99%;
                font-size: 3.6vmax;
            }
            .r60 {
                width: 59.99%;
                font-size: 3.6vmax;
            }
            .r40 {
                width: 39.99%;
                font-size: 3.6vmax;
            }
            .r20 {
                width: 19.99%;
                font-size: 3.6vmax;
            }
        </style>
        """
        }
        body {
        """
        <table class="flat-table">
            <thead>
                <th class="h40">Network Address</th>
                <th class="h20">Port</th>
                <th class="h40">Debug</th>
            </thead>
            <tbody>
                <tr>
                    <td class="r40">${_ip_address}</td>
                    <td class="r20">${_ip_port}</td>
                    <td class="r40">${_debug_mode}</td>
                </tr>
            </tbody>
        </table>
        <table class="flat-table">
            <thead>
                <th class="h100">Stream URI</th>
            </thead>
            <tbody>
                <tr>
                    <td class="r100">${_stream_uri}</td>
                </tr>
            </tbody>
        </table>
        """
        }
    }
  }
