# Copyright (C) 2019 Zhu Yi <dev.yi.zhu@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Utilities useful for test rpi-home device."
LICENSE = "MIT"

inherit packagegroup

OMXPLAYER_rpi = "omxplayer"
OMXPLAYER_rpi_aarch64 = ""

RDEPENDS_${PN} = " \
    ${OMXPLAYER} \
    bcm2835-tests \
    bluez5 \
"

# bigbuckbunny-480p
# bigbuckbunny-720p
RRECOMMENDS_${PN} = " \
    bigbuckbunny-1080p \
    tearsofsteel-1080p \
"
