FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# see http://0pointer.de/blog/projects/instances.html 
do_install_append () {
    install -d ${D}${sysconfdir}/wpa_supplicant
    mv ${D}${sysconfdir}/wpa_supplicant.conf ${D}${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf

    install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
    cd ${D}${sysconfdir}/systemd/system/multi-user.target.wants/ && \
    ln -sf ${systemd_system_unitdir}/wpa_supplicant@.service wpa_supplicant@wlan0.service
}
