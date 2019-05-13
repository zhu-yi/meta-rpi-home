# Copyright (C) 2019 Zhu Yi <dev.yi.zhu@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "A LMS image for rpi-home device."
LICENSE = "MIT"

require rpi-home-base.bb

IMAGE_INSTALL += "  \
    slimserver \
    squeezelite \
    jivelite \
"
