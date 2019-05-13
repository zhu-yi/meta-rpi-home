FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Let systemd to up Ethernet and WiFi interface automatically
SRC_URI += " \
    file://20-wired.network \
    file://25-wireless.network \
"

do_install_append () {
    # Install Ethernet and WiFi link configuration file
    install -d ${D}${sysconfdir}/systemd/
    install -m 0644 ${WORKDIR}/20-wired.network ${D}${sysconfdir}/systemd/network/20-wired.network
    install -m 0644 ${WORKDIR}/25-wireless.network ${D}${sysconfdir}/systemd/network/25-wireless.network
}

FILES_{PN} += " \
    ${sysconfdir}/systemd/network/20-wired.network \
    ${sysconfdir}/systemd/network/25-wireless.network \
"
